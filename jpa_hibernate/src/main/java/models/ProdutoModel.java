package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Produto;

public class ProdutoModel {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

	public void create(Produto p) {
		EntityManager em = getEntityManager();

		try {
			System.out.println("Iniciando a transação");
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			System.out.println("Produto criado com sucesso !!!");
		} catch (Exception e) {
			em.close();
			System.err.println("Erro ao criar o produto !!!" + e.getMessage());
		} finally {
			em.close();
			System.out.println("Finalizando a transação");
		}
	}

	public void update(Produto p) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.close();
	}

	public void delete(Produto p) {
		EntityManager em = getEntityManager();
	    try {
	        em.getTransaction().begin();
	        Produto produtoGerenciado = em.find(Produto.class, p.getId());
	        if (produtoGerenciado != null) {
	            em.remove(produtoGerenciado);
	        }
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        System.err.println("Erro ao deletar o produto: " + e.getMessage());
	    } finally {
	        em.close();
	    }

	}

	public Produto findById(Produto p) {
		EntityManager em = getEntityManager();
		Produto produto = em.find(Produto.class, p.getId());
		em.close();
		return produto;
	}

	public List<Produto> findAll() {
		EntityManager em = getEntityManager();
		List<Produto> produtos = new ArrayList<>();
		try {
			produtos = em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
		} catch (Exception e) {
			System.err.println("Erro ao buscar produtos: " + e.getMessage());
		} finally {
			em.close();
		}

		return produtos;

	}
}

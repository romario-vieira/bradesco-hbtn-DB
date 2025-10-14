package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Pessoa;

public class PessoaModel {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

	public void create(Pessoa p) {
		EntityManager em = getEntityManager();

		try {
			System.out.println("Iniciando a transação");
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			System.out.println("Pessoa criado com sucesso !!!");
		} catch (Exception e) {
			em.close();
			System.err.println("Erro ao criar a pessoa !!!" + e.getMessage());
		} finally {
			em.close();
			System.out.println("Finalizando a transação");
		}
	}

	public void update(Pessoa p) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.close();
	}

	public void delete(Pessoa p) {
		EntityManager em = getEntityManager();
	    try {
	        em.getTransaction().begin();
	        Pessoa produtoGerenciado = em.find(Pessoa.class, p.getId());
	        if (produtoGerenciado != null) {
	            em.remove(produtoGerenciado);
	        }
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        System.err.println("Erro ao deletar o pessoa: " + e.getMessage());
	    } finally {
	        em.close();
	    }

	}

	public Pessoa findById(Pessoa p) {
		EntityManager em = getEntityManager();
		Pessoa produto = em.find(Pessoa.class, p.getId());
		em.close();
		return produto;
	}

	public List<Pessoa> findAll() {
		EntityManager em = getEntityManager();
		List<Pessoa> produtos = new ArrayList<>();
		try {
			produtos = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
		} catch (Exception e) {
			System.err.println("Erro ao buscar pessoas: " + e.getMessage());
		} finally {
			em.close();
		}

		return produtos;

	}
}

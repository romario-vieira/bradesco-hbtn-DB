package models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Curso;

public class CursoModel {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        EntityManager em = getEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
            System.out.println("Curso criado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar um curso !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Curso findById(Long id) {
        EntityManager em = getEntityManager();
        Curso cursoRetornado = em.find(Curso.class, id);
        em.close();
        return cursoRetornado;
    }

    public  List<Curso> findAll() {
        EntityManager em = getEntityManager();
        List<Curso> cursos = em.createQuery("Select c FROM Curso c", Curso.class).getResultList();
        em.close();
        return cursos;
    }

    public void update(Curso curso) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(curso); // Atualiza o curso no banco de dados
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Curso curso) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(curso) ? curso : em.merge(curso)); // Remove o curso do banco de dados
        em.getTransaction().commit();
        em.close();
        
    }
}

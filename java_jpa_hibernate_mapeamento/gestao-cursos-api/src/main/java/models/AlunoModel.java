package models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Aluno;

public class AlunoModel {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aluno aluno) {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        //EntityManager em = emf.createEntityManager();
        EntityManager em = getEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno criado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar um aluno !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Aluno findById(Long id) {
        EntityManager em = getEntityManager();
        Aluno alunoRetornado = em.find(Aluno.class, id);
        em.close();
        return alunoRetornado;
    }

    public  List<Aluno> findAll() {
        EntityManager em = getEntityManager();
        List<Aluno> alunos = em.createQuery("Select a FROM Aluno a", Aluno.class).getResultList();
        em.close();
        return alunos;
    }

    public void update(Aluno aluno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(aluno); // Atualiza o aluno no banco de dados
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Aluno aluno) {
        // TODO
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Aluno alunoToDelete = em.find(Aluno.class, aluno.getId());
        if (alunoToDelete != null) {
            em.remove(alunoToDelete); // Remove o aluno do banco de dados
        }
        em.getTransaction().commit();
        em.close();
    }
}

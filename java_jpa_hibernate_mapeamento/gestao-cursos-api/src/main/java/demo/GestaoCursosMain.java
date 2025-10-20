package demo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Aluno;
import entities.Curso;
import entities.Endereco;
import entities.MaterialCurso;
import entities.Professor;
import entities.Telefone;
import models.AlunoModel;
import models.CursoModel;

public class GestaoCursosMain {

    public static void main(String[] args) {

        System.out.println("\n--- Criando Aluno ---");
        Aluno aluno = new Aluno();
        aluno.setNomeCompleto("João da Silva");
        aluno.setMatricula("2024001");
        aluno.setNascimento(new Date());
        aluno.setEmail("joao@email.com");
        System.out.println(" --- Aluno criado: " + aluno.getNomeCompleto() + " --- \n");

        System.out.println("--- Criando Telefone ---");
        Telefone telefone = new Telefone();
        telefone.setDdd("11");
        telefone.setNumero("91234-5678");
        telefone.setAluno(aluno);
        System.out.println(" --- Telefone criado: (" + telefone.getDdd() + ") " + telefone.getNumero() + " --- \n");

        System.out.println("--- Criando Endereço ---");
        Endereco endereco = new Endereco();
        endereco.setBairro("Jardim das Palmeiras");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero("123");
        endereco.setCep(12345678);
        endereco.setAluno(aluno);
        System.out.println(" --- Endereço criado: " + endereco.getLogradouro() + ", " + endereco.getNumero() + " --- \n");

        System.out.println("--- Criando Professor ---");
        Professor professor = new Professor();
        professor.setNomeCompleto("Maria Oliveira");
        professor.setMatricula("123465798");
        professor.setEmail("professor@email.com");
        System.out.println(" --- Professor criado: " + professor.getNomeCompleto() + " --- \n");

        // Persistindo o professor diretamente
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(professor); // Persiste o professor no banco de dados
            em.getTransaction().commit();
            System.out.println("Professor salvo no banco de dados com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            System.err.println("Erro ao salvar o professor: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

        System.out.println("--- Criando Curso ---");
        MaterialCurso material = new MaterialCurso();
        material.setUrl("urlmaterial.com");

        Curso curso = new Curso();
        curso.setNome("Introdução à Programação");
        curso.setSigla("IP-101");
        
        curso.setAlunos(List.of(aluno));
        curso.setProfessor(professor);
        curso.setMaterialCurso(material);
        material.setCurso(curso);
        System.out.println(" --- Curso criado: " + curso.getNome() + " (" + curso.getSigla() + ")" + " --- \n");


        aluno.setTelefones(List.of(telefone));
        aluno.setEnderecos(List.of(endereco));
        aluno.setCursos(List.of(curso));

        AlunoModel alunoModel = new AlunoModel();
        try {
            alunoModel.create(aluno);
            System.out.println("Aluno salvo no banco de dados com sucesso !!!");
            System.out.println("Aluno encontrado: " + alunoModel.findById(Long.valueOf(1)).getNomeCompleto());
            alunoModel.findAll().forEach(a -> System.out.println("Lista de Alunos: " + a.getNomeCompleto()));
            aluno.setEmail("joaomudouemail@email.com");
            alunoModel.update(aluno);
            System.out.println("Email do aluno atualizado para: " + alunoModel.findById(aluno.getId()).getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        CursoModel cursoModel = new CursoModel();
        try {
            cursoModel.create(curso);
            System.out.println("Curso salvo no banco de dados com sucesso !!!");
            System.out.println("Curso encontrado: " + cursoModel.findById(Long.valueOf(1)).getNome());
            cursoModel.findAll().forEach(c -> System.out.println("Lista de Cursos: " + c.getNome()));
            curso.setNome("Introdução à Programação - Atualizado");
            cursoModel.update(curso);
            System.out.println("Nome do curso atualizado para: " + cursoModel.findById(curso.getId()).getNome());
            cursoModel.delete(curso);
            System.out.println("Curso deletado com sucesso !!!");

            alunoModel.delete(aluno);
            System.out.println("Aluno deletado com sucesso !!!");
            alunoModel.findAll().forEach(a -> System.out.println("Lista de Alunos após deleção: " + a.getNomeCompleto()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }


}

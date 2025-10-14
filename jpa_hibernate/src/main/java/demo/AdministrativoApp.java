package demo;

import java.time.LocalDate;
import java.util.List;

import entities.Pessoa;
import entities.Produto;
import models.PessoaModel;
import models.ProdutoModel;

public class AdministrativoApp {

    public static void main(String[] args) {
        ProdutoModel produtoModel = new ProdutoModel();

        Produto p1 = new Produto();
        p1.setNome("TV");
        p1.setPreco(300.0);
        p1.setQuantidade(100);
        p1.setStatus(true);

        // 1) Criando um produto
        produtoModel.create(p1);

        //2) Buscando todos os produtos na base de dados
        List<Produto> produtos = produtoModel.findAll();
        System.out.println("Qtde de produtos encontrados : " + produtos.size());
        
        Produto prod1 = produtoModel.findById(p1);
        System.out.println("Produto encontrado: " + prod1.getNome() + " preço " + prod1.getPreco());
        
        p1.setPreco(359.0);
        produtoModel.update(p1);
        
        Produto prod2 = produtoModel.findById(p1);
        System.out.println("Produto encontrado: " + prod2.getNome() + " preço " + prod2.getPreco());

        produtoModel.delete(p1);
        List<Produto> produtos2 = produtoModel.findAll();
        System.out.println("Qtde de produtos encontrados : " + produtos2.size());
        
        // TODO - Testar os demais metódos das classes: ProdutoModel e PessoaModel

        // Criar implementação para testar a classe PessoaModel
        PessoaModel pessoaModel = new PessoaModel();
        // Testar os métodos da classe PessoaModel aqui
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("João Silva");
        pessoa1.setEmail("pessoa@pessoa.com");
        pessoa1.setCpf("12345679812");
        pessoa1.setDataNascimento(LocalDate.of(1993, 5, 28));
        pessoa1.setIdade(32);

        pessoaModel.create(pessoa1);
        List<Pessoa> pessoas = pessoaModel.findAll();
        System.out.println("Qtde de pessoas encontradas: " + pessoas.size());
        Pessoa pessoa2 = pessoaModel.findById(pessoa1);
        System.out.println("Pessoa encontrada: " + pessoa2.getNome() + " email " + pessoa2.getEmail());
        pessoa1.setEmail("emailpessoa@pessoa.com");
        pessoaModel.update(pessoa1);
        Pessoa pessoa3 = pessoaModel.findById(pessoa1);
        System.out.println("Pessoa encontrada: " + pessoa3.getNome() + " email " + pessoa3.getEmail());
        pessoaModel.delete(pessoa1);
        List<Pessoa> pessoas2 = pessoaModel.findAll();
        System.out.println("Qtde de pessoas encontradas: " + pessoas2.size());
        


    }
}

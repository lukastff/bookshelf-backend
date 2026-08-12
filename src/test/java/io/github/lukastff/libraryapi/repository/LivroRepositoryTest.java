package io.github.lukastff.libraryapi.repository;

import io.github.lukastff.libraryapi.model.Autor;
import io.github.lukastff.libraryapi.model.GeneroLivro;
import io.github.lukastff.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("90000-90000");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Culpa das Estrelas");
        livro.setDataPublicacao(LocalDate.of(1900, 1, 1));

        Autor autor = autorRepository.findById(UUID.fromString("24ddad8f-99b7-4cb1-8ee5-0cde3c7f5643")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutoreLivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("90000-90000");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Culpa das Estrelas");
        livro.setDataPublicacao(LocalDate.of(1900, 1, 1));

        Autor autor = new Autor();
        autor.setNome("Janete");
        autor.setNacionalidade("Bolonhesa");
        autor.setDataNascimento(LocalDate.of(1999, 3, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("90000-90000");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Culpa das Estrelas");
        livro.setDataPublicacao(LocalDate.of(1900, 1, 1));

        Autor autor = new Autor();
        autor.setNome("Janete");
        autor.setNacionalidade("Bolonhesa");
        autor.setDataNascimento(LocalDate.of(1999, 3, 31));

        // livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("5e6e790d-953b-42b4-b408-d46178d846f9");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("6c48992f-9913-414b-97c4-0f75b8091cc3");
        Autor newAutor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(newAutor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("5e6e790d-953b-42b4-b408-d46178d846f9");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade() {
        UUID id = UUID.fromString("6eca9179-461c-4976-b6ae-a4e11a07b018");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("6eca9179-461c-4976-b6ae-a4e11a07b018");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = repository.findByTitulo("Culpa das Estrelas");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbn() {
        List<Livro> lista = repository.findByIsbn("90000-90000");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco() {
        var preco = BigDecimal.valueOf(399.00);
        var tituloPesquisa = "Culpa das Estrelas";

        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL() {
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros() {
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidos() {
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros() {
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest() {
        var resultado = repository.findByGenero(GeneroLivro.CIENCIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamTest() {
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.CIENCIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest() {
        repository.deleteByGenero(GeneroLivro.ROMANCE);
    }

    @Test
    void updateDataPublicacaoTest() {
        repository.updateDataPublicacao(LocalDate.of(1997, 3, 31));
    }
}
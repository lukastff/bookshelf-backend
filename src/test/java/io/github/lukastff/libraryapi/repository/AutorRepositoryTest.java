package io.github.lukastff.libraryapi.repository;

import io.github.lukastff.libraryapi.model.Autor;
import io.github.lukastff.libraryapi.model.GeneroLivro;
import io.github.lukastff.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Holandesa");
        autor.setDataNascimento(LocalDate.of(1999, 3, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("0389fe6e-5e84-4a01-840e-f306a6b945fc");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setNome("Jeremias");

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("0389fe6e-5e84-4a01-840e-f306a6b945fc");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("f0053a8d-924a-4d9a-9368-0cb56243cef9");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Tonin");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1999, 3, 31));

        Livro livro = new Livro();
        livro.setIsbn("90000-90000");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Culpa das Estrelas");
        livro.setDataPublicacao(LocalDate.of(1900, 1, 1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("90000-90000");
        livro2.setPreco(BigDecimal.valueOf(399));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Culpa das Estrelas");
        livro2.setDataPublicacao(LocalDate.of(1900, 1, 1));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
    }

    @Test
    void listarLivrosAutor() {
        var id = UUID.fromString("4e5c7d11-9a65-49ab-ae48-7a8831f52b0e");
        var autor = repository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

}

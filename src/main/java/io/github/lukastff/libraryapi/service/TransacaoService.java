package io.github.lukastff.libraryapi.service;

import io.github.lukastff.libraryapi.model.Autor;
import io.github.lukastff.libraryapi.model.GeneroLivro;
import io.github.lukastff.libraryapi.model.Livro;
import io.github.lukastff.libraryapi.repository.AutorRepository;
import io.github.lukastff.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto() {
        // salva o livro
        // repository.save(livro)

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar a foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png");
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository.findById(UUID.fromString("ad77579e-563b-410b-808c-e6c8050ad071")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2000, 1, 31));
    }

    @Transactional
    public void executar() {
        Autor autor = new Autor();
        autor.setNome("Fransisco");
        autor.setNacionalidade("Bolonhesa");
        autor.setDataNascimento(LocalDate.of(1999, 3, 31));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("90000-90000");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Culpa das Fransiscos");
        livro.setDataPublicacao(LocalDate.of(1900, 1, 1));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("SLK")) {
            throw new RuntimeException("Rollback!");
        }
    }
}

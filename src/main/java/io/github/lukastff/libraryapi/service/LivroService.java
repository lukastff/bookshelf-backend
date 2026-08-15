package io.github.lukastff.libraryapi.service;

import io.github.lukastff.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.lukastff.libraryapi.controller.dto.ErroResposta;
import io.github.lukastff.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lukastff.libraryapi.repository.LivroRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
}

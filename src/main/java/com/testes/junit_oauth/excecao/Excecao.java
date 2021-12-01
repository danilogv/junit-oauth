package com.testes.junit_oauth.excecao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class Excecao {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Erro> notFound(ResponseStatusException excecao) {
        Erro erro = new Erro();
        erro.setStatus(excecao.getStatus().value());
        erro.setMensagem(excecao.getReason());
        return ResponseEntity.status(excecao.getStatus()).body(erro);
    }

}

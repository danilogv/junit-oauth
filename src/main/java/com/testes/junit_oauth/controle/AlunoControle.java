package com.testes.junit_oauth.controle;

import com.testes.junit_oauth.dominio.Aluno;
import com.testes.junit_oauth.servico.AlunoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoControle extends ObjetoControle {

    @Autowired
    private AlunoServico servico;

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody Aluno aluno) {
        try {
            this.servico.inserir(aluno);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody Aluno aluno) {
        try {
            this.servico.alterar(aluno);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            this.servico.excluir(id);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscar(@PathVariable Integer id) {
        Aluno aluno = null;
        try {
            aluno = this.servico.buscar(id);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> buscarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            alunos = this.servico.buscarTodos();
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

}

package com.testes.junit_oauth.servico;

import com.testes.junit_oauth.dominio.Aluno;
import com.testes.junit_oauth.repositorio.AlunoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AlunoServico {

    @Autowired
    private AlunoRepositorio repositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Aluno aluno) {
        this.validaAluno(aluno,false);
        String nome = aluno.getNome();
        Integer matricula = aluno.getMatricula();
        BigDecimal coeficiente = aluno.getCoeficiente();
        this.verificaMatriculaDuplicada(null,matricula);
        this.repositorio.insere(nome,matricula,coeficiente);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(Aluno aluno) {
        this.validaAluno(aluno,true);
        Integer id = aluno.getId();
        String nome = aluno.getNome();
        Integer matricula = aluno.getMatricula();
        BigDecimal coeficiente = aluno.getCoeficiente();
        this.verificaAluno(id);
        this.repositorio.altera(nome,matricula,coeficiente,id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void excluir(Integer id) {
        this.verificaAluno(id);
        this.repositorio.remove(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public Aluno buscar(Integer id) {
        this.verificaAluno(id);
        Aluno aluno = this.repositorio.buscar(id);
        return aluno;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<Aluno> buscarTodos() {
        List<Aluno> alunos = this.repositorio.buscarTodos();
        this.verificaExistenciaAlunos(alunos);
        return alunos;
    }

    private void validaAluno(Aluno aluno,Boolean ehAlteracao) {
        if (ehAlteracao && (aluno.getId() == null || aluno.getId() <= 0)) {
            String msg = "Aluno n??o encontrado";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (aluno.getNome() == null || aluno.getNome().equals("")) {
            String msg = "Nome do aluno n??o informado";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (aluno.getMatricula() == null) {
            String msg = "Matr??cula do aluno n??o informada";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (aluno.getMatricula() <= 0) {
            String msg = "Matr??cula do aluno inv??lida";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (aluno.getCoeficiente() == null) {
            String msg = "Coeficiente do aluno n??o informado";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (aluno.getCoeficiente().compareTo(BigDecimal.ZERO) < 0 || aluno.getCoeficiente().compareTo(new BigDecimal(100)) > 0) {
            String msg = "Coeficiente do aluno inv??lido deve ser entre zero e cem";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

    private void verificaAluno(Integer id) {
        if (this.repositorio.existe(id,null).equals(0)) {
            String msg = "Aluno com o id n??o encontrado";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

    private void verificaMatriculaDuplicada(Aluno aluno,Integer matricula) {
        if (aluno == null && this.repositorio.existe(null,matricula).equals(1)) {
            String msg = "Aluno com matr??cula j?? cadastrada";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

    private void verificaExistenciaAlunos(List<Aluno> alunos) {
        if (alunos.size() == 0) {
            String msg = "N??o existem alunos cadastrados";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

}

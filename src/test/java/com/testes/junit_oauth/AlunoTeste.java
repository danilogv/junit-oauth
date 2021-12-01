package com.testes.junit_oauth;

import com.testes.junit_oauth.dominio.Aluno;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoTeste {

    @Autowired
    private MockMvc mock;

    @Autowired
    private JdbcTemplate jdbc;

    private final MediaType tipo = MediaType.APPLICATION_JSON;

    @BeforeEach
    public void inicio() {
        JdbcTestUtils.deleteFromTables(jdbc, "aluno"); //excluir tabelas do banco antes do test
        this.jdbc.execute("ALTER TABLE aluno AUTO_INCREMENT = 1");
    }

    @Test
    public void insercaoPadrao() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void insercaoNomeVazio() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void insercaoMatriculaVazia() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(null);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void insercaoMatriculaInvalida() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(-777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void insercaoCoeficienteVazio() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(null);
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void insercaoCoeficienteInvalido() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(-100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void insercaoAlunoMatriculaDuplicada() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));

        aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoPadrao() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));

        aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("Fulano de Tal 2");
        aluno.setMatricula(888);
        aluno.setCoeficiente(new BigDecimal(90.00));
        json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isNoContent());
    }

    @Test
    public void alteracaoIdVazio() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(null);
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoIdInvalido() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(-1);
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoNomeVazio() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoMatriculaVazia() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(null);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoMatriculaInvalida() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(-777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoCoeficienteVazio() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(null);
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void alteracaoCoeficienteInvalido() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(-100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(put("/aluno").contentType(this.tipo).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void remocaoPadrao() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));
        this.mock.perform(delete("/aluno/1").contentType(this.tipo)).andExpect(status().isNoContent());
    }

    @Test
    public void remocaoIdInexistente() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));
        this.mock.perform(delete("/aluno/2").contentType(this.tipo)).andExpect(status().isBadRequest());
    }

    @Test
    public void buscaPadrao() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));
        this.mock.perform(get("/aluno/1").contentType(this.tipo)).andExpect(status().isOk());
    }

    @Test
    public void buscaIdInexistente() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano de Tal");
        aluno.setMatricula(777);
        aluno.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));
        this.mock.perform(get("/aluno/2").contentType(this.tipo)).andExpect(status().isBadRequest());
    }

    @Test
    public void buscaTodosPadrao() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Fulano de Tal");
        aluno1.setMatricula(777);
        aluno1.setCoeficiente(new BigDecimal(100.00));
        String json = this.criaAluno(aluno1).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Fulano de Tal");
        aluno2.setMatricula(777);
        aluno2.setCoeficiente(new BigDecimal(100.00));
        json = this.criaAluno(aluno1).toString();
        this.mock.perform(post("/aluno").contentType(this.tipo).content(json));

        this.mock.perform(get("/aluno").contentType(this.tipo)).andExpect(status().isOk());
    }

    @Test
    public void buscaTodosVazio() throws Exception {
        this.mock.perform(get("/aluno").contentType(this.tipo)).andExpect(status().isBadRequest());
    }

    private JSONObject criaAluno(Aluno aluno) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id",aluno.getId());
        json.put("nome",aluno.getNome());
        json.put("matricula",aluno.getMatricula());
        json.put("coeficiente",aluno.getCoeficiente());
        return json;
    }

}

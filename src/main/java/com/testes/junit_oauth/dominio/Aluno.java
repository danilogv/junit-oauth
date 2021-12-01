package com.testes.junit_oauth.dominio;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "coeficiente")
    private BigDecimal coeficiente;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public BigDecimal getCoeficiente() {
        return this.coeficiente;
    }

    public void setCoeficiente(BigDecimal coeficiente) {
        this.coeficiente = coeficiente;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        Aluno aluno = (Aluno) objeto;
        if (this.id.equals(aluno.getId()) || this.matricula.equals(aluno.getMatricula())) {
            return true;
        }
        return false;
    }

}

package com.testes.junit_oauth.repositorio;


import com.testes.junit_oauth.dominio.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno,Integer> {

    @Modifying
    @Query(value =
        "INSERT INTO aluno(nome,matricula,coeficiente) " +
        "VALUES (?1,?2,?3)"
    ,nativeQuery = true)
    void insere(String nome,Integer matricula,BigDecimal coeficiente);

    @Modifying
    @Query(value =
        "UPDATE aluno " +
        "SET nome = ?1,matricula = ?2,coeficiente = ?3 " +
        "WHERE id = ?4"
    ,nativeQuery = true)
    void altera(String nome,Integer matricula,BigDecimal coeficiente,Integer id);

    @Modifying
    @Query(value =
        "DELETE FROM aluno " +
        "WHERE id = ?1"
    ,nativeQuery = true)
    void remove(Integer id);

    @Query(value =
        "SELECT * " +
        "FROM aluno " +
        "ORDER BY nome"
    ,nativeQuery = true)
    List<Aluno> buscarTodos();

    @Query(value =
        "SELECT * " +
        "FROM aluno " +
        "WHERE id = ?1"
    ,nativeQuery = true)
    Aluno buscar(Integer id);

    @Query(value =
        "SELECT COUNT(id) > 0 " +
        "FROM aluno " +
        "WHERE id = ?1 " +
        "OR matricula = ?2"
    ,nativeQuery = true)
    Integer existe(Integer id,Integer matricula);
}

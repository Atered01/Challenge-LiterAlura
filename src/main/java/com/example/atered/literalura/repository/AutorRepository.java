package com.example.atered.literalura.repository;

import com.example.atered.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoDeNascimento <= :ano AND (a.anoDaMorte IS NULL OR a.anoDaMorte >= :ano)")
    List<Autor> findAutoresVivosEmAno(@Param("ano") int ano);
}

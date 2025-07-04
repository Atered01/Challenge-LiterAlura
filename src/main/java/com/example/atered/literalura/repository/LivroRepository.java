package com.example.atered.literalura.repository;

import com.example.atered.literalura.model.Idiomas;
import com.example.atered.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Livro l JOIN FETCH l.autores")
    List<Livro> findAllWithAutores();

    List<Livro> findByIdiomas(Idiomas idioma);
}

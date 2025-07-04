package com.example.atered.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;

    private Long numeroDeDownloads;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<>();


    public Livro(DadosLivros dadosLivros) {
        this.titulo = dadosLivros.titulo();
        this.numeroDeDownloads = dadosLivros.numeroDeDownloads();
        this.idiomas = findFirstValidIdioma(dadosLivros.idiomas());
    }

    public Livro() {}


    private Idiomas findFirstValidIdioma(List<String> idiomasApi) {
        if (idiomasApi == null || idiomasApi.isEmpty()) {
            return null;
        }

        for (String codigoIdioma : idiomasApi) {
            try {
                return Idiomas.fromString(codigoIdioma);
            } catch (IllegalArgumentException e) {
            }
        }
        return null;
    }


    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Idiomas getIdiomas() { return idiomas; }
    public void setIdiomas(Idiomas idiomas) { this.idiomas = idiomas; }
    public Long getNumeroDeDownloads() { return numeroDeDownloads; }
    public void setNumeroDeDownloads(Long numeroDeDownloads) { this.numeroDeDownloads = numeroDeDownloads; }
    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }


    @Override
    public String toString() {
        String nomesAutores = autores.stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
        return String.format("""
                ----- LIVRO -----
                Título: %s
                Autor(es): %s
                Idioma: %s
                Downloads: %d
                -----------------
                """,
                titulo,
                !nomesAutores.isEmpty() ? nomesAutores : "Desconhecido",
                idiomas,
                numeroDeDownloads);
    }
}
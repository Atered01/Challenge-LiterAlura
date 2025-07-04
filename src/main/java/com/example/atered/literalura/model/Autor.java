package com.example.atered.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDaMorte;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;


    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoDeNascimento = dadosAutor.anoDeNascimento();
        this.anoDaMorte = dadosAutor.anoDaMorte();
    }

    public Autor() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public Integer getAnoDeNascimento() { return anoDeNascimento; }
    public void setAnoDeNascimento(Integer anoDeNascimento) { this.anoDeNascimento = anoDeNascimento; }
    public Integer getAnoDaMorte() { return anoDaMorte; }
    public void setAnoDaMorte(Integer anoDaMorte) { this.anoDaMorte = anoDaMorte; }

    @Override
    public String toString() {
        return String.format("""
                ----- AUTOR -----
                Nome: %s
                Ano de Nascimento: %s
                Ano de Falecimento: %s
                Livro: %s
                -----------------
                """,
                nome,
                anoDeNascimento != null ? anoDeNascimento : "N/A",
                anoDaMorte != null ? anoDaMorte : "N/A",
                livro != null ? livro.getTitulo() : "Nenhum");
    }
}
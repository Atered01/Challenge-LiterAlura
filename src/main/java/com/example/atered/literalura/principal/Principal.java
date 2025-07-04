package com.example.atered.literalura.principal;

import com.example.atered.literalura.model.*;
import com.example.atered.literalura.repository.AutorRepository;
import com.example.atered.literalura.repository.LivroRepository;
import com.example.atered.literalura.service.ConsumoApi;
import com.example.atered.literalura.service.ConverterDados;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();
    private final String ENDERECO = "https://gutendex.com";
    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private List<Livro> livros;
    private List<Autor> autores;
    //Romeo and Juliet

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    ****BEM VINDO AO BUSCALIVRO****
                    Escolha o número de sua opção:
                    1 - Buscar livro pelo titulo.
                    2 - Listar livros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos em determinado ano.
                    5 - Listar livros em determinado idioma.
                    0 - Sair.
                    """);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoreRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    listarLivrosEmDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção invalida!");
                    break;
            }
        }
    }



    private void buscarLivroPeloTitulo() {
        Optional<DadosLivros> dadosLivrosOptional = getDadosLivros();
        if (dadosLivrosOptional.isEmpty()) {
            System.out.println("Nenhum livro encontrado na API com esse título.");
            return;
        }
        DadosLivros dadosLivro = dadosLivrosOptional.get();

        // Check if the book already exists in the database
        Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(dadosLivro.titulo());

        if (livroExistente.isPresent()) {
            System.out.println("O livro '" + dadosLivro.titulo() + "' já existe no banco de dados.");
        } else {
            System.out.println("Livro não encontrado no banco. Criando novo registro...");

            // Create a new Livro object from the API data
            Livro novoLivro = new Livro(dadosLivro);

            // Process and associate authors
            if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
                List<Autor> autoresDoLivro = dadosLivro.autores().stream()
                        .map(dadosAutor -> {
                            // Check if author exists, otherwise create a new one
                            return autorRepository.findByNome(dadosAutor.nome())
                                    .orElseGet(() -> new Autor(dadosAutor));
                        })
                        .collect(Collectors.toList());

                // Set the authors on the book, and the book on each author
                novoLivro.setAutores(autoresDoLivro);
                autoresDoLivro.forEach(autor -> autor.setLivro(novoLivro));
            }

            livroRepository.save(novoLivro);
            System.out.println("Livro '" + novoLivro.getTitulo() + "' salvo com sucesso!");
            System.out.println(novoLivro);
        }
    }

    private Optional<DadosLivros> getDadosLivros() {
        System.out.println("Digite o nome do livro: ");
        var nomeLivro = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO + "/books?search=" + nomeLivro.replace(" ", "%20"));

        DadosResposta dadosResposta = conversor.obterDados(json, DadosResposta.class);

        if (dadosResposta != null && dadosResposta.resultados() != null && !dadosResposta.resultados().isEmpty()) {
            return Optional.of(dadosResposta.resultados().get(0));
        }
        return Optional.empty();
    }

    private void listarLivrosRegistrados() {
        livros = livroRepository.findAllWithAutores();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getNumeroDeDownloads))
                .forEach(System.out::println);
    }

    private void listarAutoreRegistrados() {
        autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        List<Autor>autoresVivos= autorRepository.findAutoresVivosEmAno(ano);
        for(Autor a : autoresVivos){
            System.out.println(a.toString());
        }
    }

    private void listarLivrosEmDeterminadoIdioma() {
        System.out.println("""
            Selecione o idioma do livro:
            pt - Português
            en - Inglês
            fr - Francês
            es - Espanhol
            it - Italiano
            """);
        var linguaInput = scanner.nextLine();

        try {
            Idiomas idioma = Idiomas.fromString(linguaInput);

            List<Livro> livrosEncontrados = livroRepository.findByIdiomas(idioma);

            if (livrosEncontrados.isEmpty()) {
                System.out.println("Nenhum livro encontrado para o idioma '" + idioma + "' no banco de dados.");
            } else {
                System.out.println("Livros encontrados para o idioma '" + idioma + "':");
                livrosEncontrados.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
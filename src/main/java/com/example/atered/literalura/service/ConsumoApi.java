package com.example.atered.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Falha na chamada à API! Código de status: "
                        + response.statusCode()
                        + " | Resposta: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consultar a API: " + e.getMessage(), e);
        }

        return response.body();
    }
}
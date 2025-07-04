package com.example.atered.literalura.model;

import java.util.Arrays; // Import Arrays

public enum Idiomas {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    ITALIAN("it"),
    PORTUGUESE("pt");

    private String idiomasAlura;

    Idiomas(String idiomasAlura ) {
        this.idiomasAlura = idiomasAlura;
    }

    public static Idiomas fromString(String text) {
        return Arrays.stream(Idiomas.values())
                .filter(i -> i.idiomasAlura.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum idioma encontrado para o código: " + text));
    }
}
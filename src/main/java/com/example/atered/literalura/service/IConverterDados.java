package com.example.atered.literalura.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}

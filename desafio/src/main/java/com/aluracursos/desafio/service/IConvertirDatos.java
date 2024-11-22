package com.aluracursos.desafio.service;

public interface IConvertirDatos {
    <T> T getData(String json, Class<T> clase);
}

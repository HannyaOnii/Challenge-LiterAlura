package com.aluracursos.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String title,
        @JsonAlias("authors")  List<DatosAutor> autor,
        @JsonAlias("languages")  List<String> language,
        @JsonAlias("download_count")  Double download
) {
}

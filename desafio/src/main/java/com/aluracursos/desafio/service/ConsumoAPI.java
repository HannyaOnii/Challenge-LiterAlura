package com.aluracursos.desafio.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }return response.body();
    }
}

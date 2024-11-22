package com.aluracursos.desafio.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String autor;
    private String idioma;
    private Double download;

    public Libro(DatosLibros datosLibros){
        this.title = datosLibros.title();
        this.autor = datosLibros.autor().get(0).nombre();
        this.idioma = datosLibros.language().get(0);
        this.download = datosLibros.download();

    }
    public Libro(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLanguage() {
        return idioma;
    }

    public void setLanguage( String language) {
        this.idioma = language;
    }

    public Double getDownload() {
        return download;
    }

    public void setDownload(Double download) {
        this.download = download;
    }

}

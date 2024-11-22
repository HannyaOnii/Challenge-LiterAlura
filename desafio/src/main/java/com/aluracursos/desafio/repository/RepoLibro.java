package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoLibro extends JpaRepository<Libro, Integer> {
    @Query("SELECT l FROM Libro l WHERE l.idioma ILIKE %:idioma%")
    List<Libro> findByLanguaje(String idioma);

}

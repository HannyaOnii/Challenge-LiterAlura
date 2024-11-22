package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoAutor extends JpaRepository<Autor, Integer> {
    @Query("SELECT a FROM Autor a WHERE a.birth_day >= : year ORDER BY a.birth_day ASC")
    List<Autor> getAutorByDate(Integer year);

}

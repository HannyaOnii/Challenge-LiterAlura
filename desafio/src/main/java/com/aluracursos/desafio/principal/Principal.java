package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Autor;
import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.Libro;
import com.aluracursos.desafio.repository.RepoAutor;
import com.aluracursos.desafio.repository.RepoLibro;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvertirDatos;

import java.util.*;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<Autor> autores;
    private List<Libro> libro;
    private final RepoLibro repoLibro;
    private final RepoAutor repoAutor;
    private static final String URL_BASE = "https://gutendex.com/books/";

    public Principal(RepoLibro repoLibro, RepoAutor repoAutor) {
        this.repoLibro = repoLibro;
        this.repoAutor = repoAutor;
    }


    public void IniciarApp() {
        int opcion = 0;
        while (true) {
            var menu = """
                    ================ELIJA UNA OPCION===================
                    
                    1- Buscar libro por titulo.
                    2- Mostrar libros registrados.
                    3- Mostrar autores registrados. 
                    4. Mostrar autores vivos por fecha.
                    5.Mostrar por idiomas. 
                    
                    0- Salir 
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
               case 2:
                   consultarRegistroLibros();
                    break;
                case 3:
                    consultarRegistroAutores();
                    break;
                case 4:
                    consultarFecha();
                case 5:
                    consultarIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    teclado.nextLine();
                    break;
            }
        }
    }

    //Busqueda de libros por nombre
    public Datos getDatosLibro() {
        String tituloLibro ="";
        System.out.println("Escribe el libro a consultar:  ");
        tituloLibro = teclado.nextLine();
        String finalName = URL_BASE + "?search=" + tituloLibro.replace(" ","%20");
        var json = consumoAPI.obtenerDatos(finalName);
        Datos datoslibro = conversor.getData(json, Datos.class);
        return datoslibro;
    }

    private void buscarLibro(){
      Datos datosLibros = getDatosLibro();
       if (datosLibros.resultados().isEmpty()){
           System.out.println("Ningun resultado");
           System.out.println("Presione enter para continuar");
           teclado.nextLine();
       } else {
           Libro libro = new Libro(datosLibros.resultados().get(0));
           libro.setTitle(datosLibros.resultados().get(0).title().length() > 240 ? datosLibros.resultados().get(0).title().substring(0,240): datosLibros.resultados().get(0).title());
           Autor autor = new Autor(datosLibros.resultados().get(0).autor().get(0));
           try {
               System.out.println("*******************RESULTADOS DE LA BUSQUEDA*******************");
               System.out.printf("""
                       Titulo: %s
                       Autor:  %s
                       Idioma: %s
                       Descargas: %s
                       """, libro.getTitle(), libro.getAutor(), libro.getLanguage(), libro.getDownload().toString());
               System.out.println("*******************************");
               repoLibro.save(libro);
               repoAutor.save(autor);
               System.out.println("Presione enter para continuar...");
               teclado.nextLine();
           } catch (Exception e){
               throw new RuntimeException(e);
           }
       }
    }
    private void consultarRegistroLibros(){
        try {
            libro = repoLibro.findAll();
            if (libro.isEmpty()) {
                System.out.println("No se encontraron libros registrados");
                System.out.println("Presione enter para continuar");
                teclado.nextLine();
            } else {
                libro.forEach(l -> {
                    System.out.printf("""
                            Titulo: %s
                            Autor:  %s
                            Idioma: %s
                            Descargas: %s
                            %n""", l.getTitle(), l.getAutor(), l.getLanguage(), l.getDownload().toString());
                    System.out.println("*******************************");
                });
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
private void consultarRegistroAutores() {
    autores = repoAutor.findAll();
    try {
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados");
            System.out.println("Presione Enter para continuar");
            teclado.nextLine();
        } else {
            autores.forEach(a -> {
                System.out.printf("""
                        Autor: %s
                        Nacimiento: %s
                        Fallecimiento: %s
                        %n""", a.getName(), a.getBirth_day() != null ? a.getBirth_day().toString() : "Fecha de Nacimiento no encontrada", a.getDeath_day() != null ? a.getDeath_day().toString() : "En la actualidad");
            });
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
private void consultarFecha() {
    int autor = 0;
    while (autor == 0) {
        System.out.println("Ingrese el ano para buscar autores");
        autor = teclado.nextInt();
        if (autor == 0) {
            System.out.println("Digite un ano para buscar autores en el intervalo");
            teclado.nextLine();
        } else {
            List<Autor> autores = repoAutor.getAutorByDate(autor);
            autores.forEach(a -> {
                System.out.printf("""
                        Autor: %s
                        Nacimiento: %s
                        Fallecimiento: %s
                                %n""", a.getName(), a.getBirth_day() != null ? a.getBirth_day().toString() : "Fecha de Nacimiento no encontrada", a.getDeath_day() != null ? a.getDeath_day().toString() : "En la actualidad");
            });
        }
    }
}
        private void consultarIdioma() {
            int idioma = 0;
            while(idioma == 0){
                System.out.println("""
                        Elija el idioma a consultar:
                        1. en - Ingles
                        2. es - Espanol
                        3. fr - Frances
                        4. de - Aleman
                        5. it - Italiano""");
                idioma = teclado.nextInt();
                switch (idioma){
                    case 1:
                        libro = repoLibro.findByLanguaje("en");
                        break;
                    case 2:
                        libro = repoLibro.findByLanguaje("es");
                        break;
                    case 3:
                        libro = repoLibro.findByLanguaje("fr");
                        break;
                    case 4:
                        libro = repoLibro.findByLanguaje("de");
                        break;
                    case 5:
                        libro = repoLibro.findByLanguaje("it");
                        break;
                    default:
                        System.out.println("Opcion no valida, presione Enter para reintentar");
                        teclado.nextLine();
                        break;
                }
            }
            if (libro.isEmpty()){
                System.out.println("No hay libros en el idioma seleccionado");
            } else {
                libro.forEach(l -> {
                    System.out.printf("""
                            Libro: %s
                            Autor:  %s
                            Idioma: %s
                            Descargas: %s
                            %n""", l.getTitle(), l.getAutor(), l.getLanguage(), l.getDownload().toString());
                });
                teclado.nextLine();
            }
    }
}



import java.util.List;

public class Book {
    private String titulo, autor;
    private int isbn;
    private List<Object> calificaciones;
    private int calificacionPromedio;

    public Book(String titulo, String autor, int isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
    }

}

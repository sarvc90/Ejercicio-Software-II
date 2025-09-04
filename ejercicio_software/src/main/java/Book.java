import java.util.ArrayList;
import java.util.List;

public class Book {
    private String titulo, autor;
    private int isbn;
    private List<Integer> calificaciones;
    private double calificacionPromedio;

    public Book(String titulo, String autor, int isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.calificaciones = new ArrayList<>();
        this.calificacionPromedio = 0.0;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getIsbn() { return isbn; }
    public List<Integer> getCalificaciones() { return calificaciones; }
    public double getCalificacionPromedio() { return calificacionPromedio; }

    public void agregarCalificacion(int calificacion) {
        calificaciones.add(calificacion);
        calcularPromedio();
    }

    private void calcularPromedio() {
        if (calificaciones.isEmpty()) {
            calificacionPromedio = 0.0;
            return;
        }
        double suma = 0;
        for (int calificacion : calificaciones) {
            suma += calificacion;
        }
        calificacionPromedio = suma / calificaciones.size();
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + ", Título: " + titulo + ", Autor: " + autor + 
               ", Calificación Promedio: " + calificacionPromedio;
    }
}
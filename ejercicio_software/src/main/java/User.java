import java.sql.*;
import java.util.Scanner;

public class User {
    private String nombre, identificacion;
    private Connection conexion;

    // Modificar constructor para recibir la conexión
    public User(String nombre, String identificacion, Connection conexion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.conexion = conexion;
    }

    public void calificarLibro(Library biblioteca) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese ISBN del libro a calificar: ");
        int isbn = scanner.nextInt();
        scanner.nextLine(); 
        
        Book libro = biblioteca.buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }
        
        System.out.print("Ingrese calificación (1-5): ");
        int calificacion = scanner.nextInt();
        scanner.nextLine();
        
        if (calificacion < 1 || calificacion > 5) {
            System.out.println("Calificación debe estar entre 1 y 5");
            return;
        }
        
        libro.agregarCalificacion(calificacion);
        guardarCalificacionEnBD(isbn, calificacion);
        System.out.println("Calificación registrada exitosamente");
    }

    private void guardarCalificacionEnBD(int isbn, int calificacion) {
        try {
            String query = "INSERT INTO calificaciones (isbn, calificacion, usuario_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, isbn);
            pstmt.setInt(2, calificacion);
            pstmt.setString(3, identificacion);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error al guardar calificación: " + e.getMessage());
        }
    }

    public void crearReseniaLibro(Library biblioteca) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese ISBN del libro para reseña: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();
        
        Book libro = biblioteca.buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }
        
        System.out.print("Escriba su reseña: ");
        String resenia = scanner.nextLine();
        
        guardarReseniaEnBD(isbn, resenia);
        System.out.println("Reseña guardada exitosamente");
    }

    private void guardarReseniaEnBD(int isbn, String resenia) {
        try {
            String query = "INSERT INTO resenias (isbn, resenia, usuario_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, isbn);
            pstmt.setString(2, resenia);
            pstmt.setString(3, identificacion);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error al guardar reseña: " + e.getMessage());
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }
}
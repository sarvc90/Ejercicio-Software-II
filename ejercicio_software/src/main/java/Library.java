import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private String nombre;
    private List<Book> libros;
    private Connection conexion;

    public Library(String nombre) {
        this.nombre = nombre;
        this.libros = new ArrayList<>();
        conectarBD();
        if (conexion != null) {
            cargarLibrosDesdeBD();
        }
    }

    private void conectarBD() {
        try {
            // CONEXIÓN PARA DOCKER
            String url = "jdbc:sqlserver://localhost:1433;databaseName=biblioteca;encrypt=true;trustServerCertificate=true;";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver cargado correctamente");

            // USUARIO Y CONTRASEÑA DE DOCKER
            String usuario = "sa";
            String contraseña = "Password123*";

            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("✅ Conexión exitosa a Docker");

        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            System.out.println("📚 Usando datos de prueba...");
            // Datos temporales si falla
            libros.add(new Book("Cien años de soledad", "Gabriel García Márquez", 123456));
            libros.add(new Book("1984", "George Orwell", 789012));
            libros.add(new Book("El Quijote", "Miguel de Cervantes", 345678));
        }
    }

     private void cargarLibrosDesdeBD() {
        if (conexion == null) {
            System.out.println("❌ No hay conexión a la base de datos");
            return;
        }
        
        try {
            String query = "SELECT isbn, titulo, autor FROM libros";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int isbn = rs.getInt("isbn");
                
                libros.add(new Book(titulo, autor, isbn));
            }
            System.out.println("📚 " + libros.size() + " libros cargados desde la BD");
            
        } catch (SQLException e) {
            System.out.println("❌ Error al cargar libros: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public void buscarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese título o autor a buscar: ");
        String busqueda = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Book libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(busqueda) ||
                    libro.getAutor().toLowerCase().contains(busqueda)) {
                System.out.println(libro);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron libros con esa búsqueda");
        }
    }

    public void buscarLibroPorCombinacion() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine().toLowerCase();
        System.out.print("Ingrese autor: ");
        String autor = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Book libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(titulo) &&
                    libro.getAutor().toLowerCase().contains(autor)) {
                System.out.println(libro);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron libros con esa combinación");
        }
    }

    public Book buscarLibroPorISBN(int isbn) {
        for (Book libro : libros) {
            if (libro.getIsbn() == isbn) {
                return libro;
            }
        }
        return null;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
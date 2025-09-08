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
            String url = "jdbc:sqlserver://localhost:1433;databaseName=biblioteca;encrypt=true;trustServerCertificate=true;";

            // Carga del driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver cargado correctamente");

            String usuario = "sa";
            String contraseña = "Password123*";

            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("- Conexión exitosa a Docker");

        } catch (Exception e) {
            //Si no conecta la base de datos, se cargan datos de prueba
            System.out.println("- Error de conexión: " + e.getMessage());
            System.out.println("- Usando datos de prueba...");
            libros.add(new Book("Cien años de soledad", "Gabriel García Márquez", 123456));
            libros.add(new Book("1984", "George Orwell", 789012));
            libros.add(new Book("El Quijote", "Miguel de Cervantes", 345678));
        }
    }

     private void cargarLibrosDesdeBD() {
         // Verifica si existe conexión a la BD
        if (conexion == null) {
            System.out.println("- No hay conexión a la base de datos");
            return;
        }
        
        try {
            // Consulta SQL para obtener libros
            String query = "SELECT isbn, titulo, autor FROM libros";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Recorre resultados y agrega cada libro a la lista
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int isbn = rs.getInt("isbn");
                
                libros.add(new Book(titulo, autor, isbn));
            }
            // Muestra la cantidad de libros cargados
            System.out.println("📚 " + libros.size() + " libros cargados desde la BD");
            
        } catch (SQLException e) {
            // Captura y muestra errores al cargar
            System.out.println("Error al cargar libros: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public void buscarLibro() {
        // Pide al usuario título o autor
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese título o autor a buscar: ");
        String busqueda = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Book libro : libros) {
            // Verifica coincidencia en título o autor
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
        System.out.print("Ingrese ISBN: ");
        String isbnInput = scanner.nextLine().trim();

        // Convierte ISBN a número si fue ingresado
        Integer isbn = null;
        if (!isbnInput.isEmpty()) {
            try {
                isbn = Integer.parseInt(isbnInput);
            } catch (NumberFormatException e) {
                System.out.println("Hubo un problema");
            }
        }

        boolean encontrado = false;
        for (Book libro : libros) {
            // Verifica coincidencia en título y autor
            boolean coincide = libro.getTitulo().toLowerCase().contains(titulo) &&
                    libro.getAutor().toLowerCase().contains(autor);

            // Si se ingresó ISBN, también lo valida
            if (isbn != null) {
                coincide = coincide && libro.getIsbn() == isbn;
            }

            // Muestra el libro si cumple con los criterios
            if (coincide) {
                System.out.println(libro);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron libros con esa combinación.");
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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library biblioteca = new Library("Mi Biblioteca");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SISTEMA DE BIBLIOTECA ===");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su identificación: ");
        String id = scanner.nextLine();
        
 
        User usuario = new User(nombre, id, biblioteca.getConexion());
        
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Buscar libro");
            System.out.println("2. Buscar libro por combinación");
            System.out.println("3. Calificar libro");
            System.out.println("4. Crear reseña de libro");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (opcion) {
                case 1:
                    biblioteca.buscarLibro();
                    break;
                case 2:
                    biblioteca.buscarLibroPorCombinacion();
                    break;
                case 3:
                    usuario.calificarLibro(biblioteca);
                    break;
                case 4:
                    usuario.crearReseniaLibro(biblioteca);
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
            
        } while (opcion != 5);
        
        biblioteca.cerrarConexion();
        scanner.close();
    }
}
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library biblioteca = new Library("Mi Biblioteca");
        Scanner scanner = new Scanner(System.in);

        //Inicio del Menú
        System.out.println("=== SISTEMA DE BIBLIOTECA ===");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su identificación: ");
        String id = scanner.nextLine();

        User usuario = new User(nombre, id, biblioteca.getConexion());

        //Menú
        int opcion = 0;
        do {
            try {
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
                        //Si son libros de datos de prueba, no se podrá calificar.
                        usuario.calificarLibro(biblioteca);
                        break;
                    case 4:
                        //Si son libros de datos de prueba, no se podrá reseñar.
                        usuario.crearReseniaLibro(biblioteca);
                        break;
                    case 5:
                        System.out.println("\nGracias por usar el sistema");
                        break;
                    default:
                        System.out.println("\nOpción no válida (" + opcion + "). Por favor ingrese un número del 1 al 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida. Debe ingresar un número del 1 al 5.");
                scanner.nextLine();
            }
        } while (opcion != 5);

        biblioteca.cerrarConexion();
        scanner.close();
    }
}

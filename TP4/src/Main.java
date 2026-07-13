import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Punto de entrada. Menú en bucle continuo para gestionar la agenda de contactos.
 */
public class Main {

    private static Scanner sc;
    private static AgendaManager agenda;

    public static void main(String[] args) {
        // Fuerza UTF-8 en entrada/salida para que tildes y ñ se muestren bien
        // sin depender del locale del sistema operativo.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        sc = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println(ConsoleColors.BOLD + ConsoleColors.BLUE +
                "===========================================\n" +
                "        AGENDA DE CONTACTOS SEGURA\n" +
                "===========================================" + ConsoleColors.RESET);

        agenda = new AgendaManager();
        System.out.println(ConsoleColors.exito(agenda.getContactos().size() + " contacto(s) cargado(s) desde agenda.dat"));

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "1": agregarContacto(); break;
                case "2": quitarContacto(); break;
                case "3": editarContacto(); break;
                case "4": mostrarAgenda(); break;
                case "5":
                    continuar = false;
                    System.out.println(ConsoleColors.info("Hasta luego."));
                    break;
                default:
                    System.out.println(ConsoleColors.error("Opción inválida. Elegí un número del 1 al 5."));
            }
        }
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD + "----------- MENÚ -----------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "1) " + ConsoleColors.RESET + "Agregar contacto");
        System.out.println(ConsoleColors.CYAN + "2) " + ConsoleColors.RESET + "Quitar contacto");
        System.out.println(ConsoleColors.CYAN + "3) " + ConsoleColors.RESET + "Editar contacto");
        System.out.println(ConsoleColors.CYAN + "4) " + ConsoleColors.RESET + "Mostrar agenda");
        System.out.println(ConsoleColors.CYAN + "5) " + ConsoleColors.RESET + "Salir");
        System.out.print(ConsoleColors.BOLD + "Elegí una opción: " + ConsoleColors.RESET);
    }

    private static void agregarContacto() {
        System.out.println(ConsoleColors.BOLD + "-- Agregar nuevo contacto --" + ConsoleColors.RESET);

        String nombre = pedirCampo("Nombre: ");
        if (nombre.isEmpty()) {
            System.out.println(ConsoleColors.error("El nombre no puede estar vacío. Operación cancelada."));
            return;
        }
        if (agenda.buscarPorNombre(nombre) != null) {
            System.out.println(ConsoleColors.error("Ya existe un contacto con ese nombre."));
            return;
        }
        String telefono = pedirCampo("Teléfono: ");
        String email = pedirCampo("Email: ");
        String nota = pedirCampo("Nota privada (se guardará encriptada): ");

        agenda.agregar(new Contacto(nombre, telefono, email, nota));
        System.out.println(ConsoleColors.exito("Contacto \"" + nombre + "\" agregado y guardado."));
    }

    private static void quitarContacto() {
        System.out.println(ConsoleColors.BOLD + "-- Quitar contacto --" + ConsoleColors.RESET);
        System.out.print("Nombre del contacto a eliminar: ");
        String nombre = sc.nextLine().trim();

        if (agenda.quitarPorNombre(nombre)) {
            System.out.println(ConsoleColors.exito("Contacto \"" + nombre + "\" eliminado y cambios guardados."));
        } else {
            System.out.println(ConsoleColors.error("No se encontró ningún contacto con ese nombre."));
        }
    }

    private static void editarContacto() {
        System.out.println(ConsoleColors.BOLD + "-- Editar contacto --" + ConsoleColors.RESET);
        System.out.print("Nombre del contacto a editar: ");
        String nombre = sc.nextLine().trim();

        Contacto c = agenda.buscarPorNombre(nombre);
        if (c == null) {
            System.out.println(ConsoleColors.error("No se encontró ningún contacto con ese nombre."));
            return;
        }

        System.out.println(ConsoleColors.info("Dejá el campo vacío para conservar el valor actual."));

        String nuevoTelefono = pedirCampo("Teléfono [" + c.getTelefono() + "]: ");
        if (!nuevoTelefono.isEmpty()) c.setTelefono(nuevoTelefono);

        String nuevoEmail = pedirCampo("Email [" + c.getEmail() + "]: ");
        if (!nuevoEmail.isEmpty()) c.setEmail(nuevoEmail);

        String nuevaNota = pedirCampo("Nota privada [******]: ");
        if (!nuevaNota.isEmpty()) c.setNotaPrivada(nuevaNota);

        agenda.guardar();
        System.out.println(ConsoleColors.exito("Contacto \"" + c.getNombre() + "\" actualizado y guardado."));
    }

    private static void mostrarAgenda() {
        List<Contacto> contactos = agenda.getContactos();
        System.out.println(ConsoleColors.BOLD + ConsoleColors.PURPLE + "-- Agenda de contactos --" + ConsoleColors.RESET);

        if (contactos.isEmpty()) {
            System.out.println(ConsoleColors.alerta("La agenda está vacía."));
            return;
        }

        String formato = "%-20s %-15s %-25s %-30s%n";
        System.out.printf(ConsoleColors.BOLD + ConsoleColors.WHITE + formato + ConsoleColors.RESET,
                "NOMBRE", "TELÉFONO", "EMAIL", "NOTA PRIVADA");
        System.out.println(ConsoleColors.WHITE + "-".repeat(90) + ConsoleColors.RESET);

        for (Contacto c : contactos) {
            // La nota ya está en texto plano en memoria; se desencripta al cargar
            // desde disco (ver AgendaManager#cargarDesdeDisco), aquí solo se muestra.
            System.out.printf(formato, c.getNombre(), c.getTelefono(), c.getEmail(), c.getNotaPrivada());
        }
    }

     private static String pedirCampo(String etiqueta) {
        System.out.print(etiqueta);
        String valor = sc.nextLine().trim();
        if (valor.contains(";")) {
            valor = valor.replace(";", ",");
            System.out.println(ConsoleColors.alerta("El carácter ';' está reservado como separador; fue reemplazado por ','."));
        }
        return valor;
    }
}
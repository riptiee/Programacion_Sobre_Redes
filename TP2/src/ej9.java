import java.io.*;
import java.util.ArrayList;

public class ej9 {
    static String ruta = "clima.txt";

    public static void main(String[] args) {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        int opcion = 0;

        do {
            System.out.println("\n--- MENU CLIMA ---");
            System.out.println("1) Cargar registro");
            System.out.println("2) Mostrar todos los registros");
            System.out.println("3) Borrar un registro por fecha");
            System.out.println("4) Salir");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(teclado.readLine());
                switch (opcion) {
                    case 1: cargar(teclado); break;
                    case 2: mostrar(); break;
                    case 3: borrar(teclado); break;
                    case 4: System.out.println("Chau."); break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error en la entrada: " + e.getMessage());
            }
        } while (opcion != 4);
    }

    static void cargar(BufferedReader teclado) {
        try {
            System.out.print("Fecha (dd/mm/aaaa): ");
            String fecha = teclado.readLine();
            System.out.print("Temperatura: ");
            String temp = teclado.readLine();
            System.out.print("Descripción (soleado, lluvia, etc): ");
            String desc = teclado.readLine();

            try (PrintWriter escritor = new PrintWriter(new FileWriter(ruta, true))) {
                escritor.println(fecha + ";" + temp + ";" + desc);
                System.out.println("Registro guardado.");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    static void mostrar() {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("Todavía no hay registros.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            System.out.println("--- Registros ---");
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 3) {
                    System.out.println("Fecha: " + partes[0] + " | Temp: " + partes[1] + " | " + partes[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    static void borrar(BufferedReader teclado) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("No hay registros para borrar.");
            return;
        }

        try {
            System.out.print("Fecha del registro a borrar: ");
            String fechaBuscada = teclado.readLine();

            ArrayList<String> registros = new ArrayList<>();
            boolean encontrado = false;

            try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] partes = linea.split(";");
                    if (partes.length > 0 && partes[0].equals(fechaBuscada)) {
                        encontrado = true;
                    } else {
                        registros.add(linea);
                    }
                }
            }

            if (encontrado) {
                try (PrintWriter escritor = new PrintWriter(new FileWriter(ruta))) {
                    for (String r : registros) {
                        escritor.println(r);
                    }
                }
                System.out.println("Registro borrado.");
            } else {
                System.out.println("No se encontró esa fecha.");
            }

        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
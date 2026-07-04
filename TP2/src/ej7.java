import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ej7 {
    public static void main(String[] args) {
        String ruta = "DOCS/caracteres.dat";
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        try (PrintWriter escritor = new PrintWriter(new FileWriter(ruta))) {
            for (int i = 1; i <= 10; i++) {
                System.out.print("Ingrese palabra " + i + " (con 'ñ'): ");
                String palabra = teclado.readLine();
                escritor.println(palabra);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        System.out.println("\nFichero original:");
        mostrarArchivo(ruta);

        List<String> lineas = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String nuevaLinea = linea.replaceAll("ñ([aeiouAEIOU])", "ni$1");
                lineas.add(nuevaLinea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        try (PrintWriter reescritor = new PrintWriter(new FileWriter(ruta))) {
            for (String l : lineas) {
                reescritor.println(l);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }

        System.out.println("\nFichero arreglado:");
        mostrarArchivo(ruta);
    }

    static void mostrarArchivo(String ruta) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al mostrar: " + e.getMessage());
        }
    }
}
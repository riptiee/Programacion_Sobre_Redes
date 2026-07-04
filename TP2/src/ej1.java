import java.io.*;

public class ej1 {
    public static void main(String[] args) {

        try (PrintWriter escritor = new PrintWriter(new FileWriter("DOCS/ultimo.txt"))) {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String dato;

            System.out.println("Escriba datos (escriba 'fin' para terminar):");

            do {
                dato = teclado.readLine(); // Leer dentro del bucle

                if (!dato.equalsIgnoreCase("fin")) {
                    escritor.println(dato);
                }
            } while (!dato.equalsIgnoreCase("fin"));

            System.out.println("Archivo guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al manejar el archivo: " + e.getMessage());
        }
    }
}
import java.io.*;

public class ej8 {
    public static void main(String[] args) {

        StringBuilder contenido = new StringBuilder();

        try (BufferedReader lector = new BufferedReader(new FileReader("DOCS/pagina.html"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.contains("Lorem")) {
                    contenido.append(linea).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        try (PrintWriter escritor = new PrintWriter(new FileWriter("DOCS/pagina.html"))) {
            escritor.print(contenido.toString());
            System.out.println("Se eliminaron las líneas con 'Lorem' del HTML correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
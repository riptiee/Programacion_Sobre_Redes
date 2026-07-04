import java.io.*;

public class ej2 {
    public static void main(String[] args) throws IOException {

        System.out.println("Ingrese datos (escriba 'fin' para terminar):");

        try(PrintWriter escritor = new PrintWriter(new FileWriter("numericos.txt", true));){
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String dato;
            do {
                dato = teclado.readLine();
                if (!dato.equalsIgnoreCase("fin") && esNumerico(dato)) {
                    escritor.println(dato);
                }
            } while (!dato.equalsIgnoreCase("fin"));
        }catch (NumberFormatException e){
            System.out.println("No es un numero" + e.getMessage());
        }
        System.out.println("Listo, numericos.txt tiene todos los valores numéricos.");
    }

    static boolean esNumerico(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
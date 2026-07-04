import java.io.*;
import java.util.ArrayList;

public class ej5 {
    public static void main(String[] args) throws IOException {
        String ruta = "DOCS/numeros.txt";
        ArrayList<Integer> numerosValidos = new ArrayList<>();

        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        String linea;
        while ((linea = lector.readLine()) != null) {
            int numero = Integer.parseInt(linea);
            if (numero % 3 != 0) {
                numerosValidos.add(numero);
            }
        }
        lector.close();

        PrintWriter escritor = new PrintWriter(new FileWriter(ruta));
        for (int n : numerosValidos) {
            escritor.println(n);
        }
        escritor.close();

        System.out.println("Se eliminaron los múltiplos de 3 de numeros.txt.");
    }
}
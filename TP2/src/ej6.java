import java.io.*;

public class ej6 {
    public static void main(String[] args){

        try(BufferedReader lector = new BufferedReader(new FileReader("DOCS/numeros.txt"))){
            PrintWriter escritor = new PrintWriter(new FileWriter("DOCS/primos.dat"));
            String linea;

            while ((linea = lector.readLine()) != null) {
                int numero = Integer.parseInt(linea);
                if (esPrimo(numero)) {
                    escritor.println(numero);
                }
            }
            lector.close();
            escritor.close();
        }catch(IOException e){
            System.out.println("IO proble" + e.getMessage());

        }
        System.out.println("primos.dat generado.");
    }

    static boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
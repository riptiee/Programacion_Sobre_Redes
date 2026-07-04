import java.io.*;

public class ej3 {
    public static void main(String[] args){

        try(PrintWriter escritor = new PrintWriter(new FileWriter("DOCS/numeros.txt"))) {
            for (int i = 0; i <= 1000; i += 2) {
                escritor.println(i);
            }
            System.out.println("Archivo numeros.txt creado existosamente");
        }catch (IOException e){
            System.out.println("No se puede crear el archivo: " + e.getMessage());
        }
    }
}
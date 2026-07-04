import java.io.*;

public class ej4 {
    public static void main(String[] args){

        try(BufferedReader lector = new BufferedReader(new FileReader("DOCS/numeros.txt"))){
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
        }catch (IOException e){
            System.out.println("no se encontraban numeros" + e.getMessage());

        }
    }
}
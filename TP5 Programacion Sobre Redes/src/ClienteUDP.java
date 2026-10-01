import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 9876;

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress direccion = InetAddress.getByName(HOST);
            Scanner teclado = new Scanner(System.in);

            System.out.println("Escriba mensajes (FIN para terminar):");

            while (true) {
                System.out.print("> ");
                String texto = teclado.nextLine();

                byte[] datos = texto.getBytes();
                DatagramPacket paquete = new DatagramPacket(datos, datos.length, direccion, PUERTO);
                socket.send(paquete);

                if (texto.equals("FIN")) {
                    System.out.println("Cerrando cliente.");
                    break;
                }
            }
            socket.close();
            teclado.close();

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
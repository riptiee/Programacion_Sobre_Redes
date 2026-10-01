import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        final int PUERTO = 9876;

        try {
            DatagramSocket socket = new DatagramSocket(PUERTO);
            System.out.println("Servidor UDP escuchando en el puerto " + PUERTO + "...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

                socket.receive(paquete);

                String mensaje = new String(paquete.getData(), 0, paquete.getLength());

                System.out.println("Mensaje recibido: " + mensaje);
                System.out.println("  Origen -> IP: " + paquete.getAddress().getHostAddress()
                        + " | Puerto: " + paquete.getPort());

                if (mensaje.equals("FIN")) {
                    System.out.println("El cliente envió FIN. Cerrando servidor.");
                    break;
                }
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
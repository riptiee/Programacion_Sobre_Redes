import java.io.*;
import java.util.ArrayList;

public class Main {

    // Códigos de color para la consola
    static final String RESET    = "\u001B[0m";
    static final String ROJO     = "\u001B[31m";
    static final String VERDE    = "\u001B[32m";
    static final String AMARILLO = "\u001B[33m";
    static final String AZUL     = "\u001B[34m";
    static final String CYAN     = "\u001B[36m";

    static final String ARCHIVO = "Inventario.dat";
    static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: agregarProducto(); break;
                case 2: mostrarProductos(); break;
                case 3: editarProducto(); break;
                case 4: eliminarProducto(); break;
                case 5: System.out.println(VERDE + "Saliendo del sistema..." + RESET); break;
                default: System.out.println(ROJO + "Opción inválida, intente de nuevo." + RESET);
            }

        } while (opcion != 5);
    }

    static void mostrarMenu() {
        System.out.println(CYAN + "\n========================================" + RESET);
        System.out.println(CYAN + "        SISTEMA DE INVENTARIO" + RESET);
        System.out.println(CYAN + "========================================" + RESET);
        System.out.println(AMARILLO + " 1." + RESET + " Agregar producto");
        System.out.println(AMARILLO + " 2." + RESET + " Mostrar productos");
        System.out.println(AMARILLO + " 3." + RESET + " Editar producto");
        System.out.println(AMARILLO + " 4." + RESET + " Eliminar producto");
        System.out.println(AMARILLO + " 5." + RESET + " Salir");
        System.out.println(CYAN + "========================================" + RESET);
    }

    static String leerTexto(String mensaje) throws IOException {
        System.out.print(mensaje);
        return teclado.readLine();
    }

    static boolean esEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean esDecimal(String texto) {
        try {
            Float.parseFloat(texto);
            return !esEntero(texto);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean esNumerico(String texto) {
        return esEntero(texto) || esDecimal(texto);
    }

    static int leerEntero(String mensaje) throws IOException {
        String texto;
        do {
            texto = leerTexto(mensaje);
            if (!esEntero(texto)) {
                System.out.println(ROJO + "Debe ingresar un número entero." + RESET);
            }
        } while (!esEntero(texto));
        return Integer.parseInt(texto);
    }

    static float leerFloat(String mensaje) throws IOException {
        String texto;
        do {
            texto = leerTexto(mensaje);
            if (!esNumerico(texto)) {
                System.out.println(ROJO + "Debe ingresar un número válido." + RESET);
            }
        } while (!esNumerico(texto));
        return Float.parseFloat(texto);
    }

    static String leerTextoObligatorio(String mensaje) throws IOException {
        String texto;
        do {
            texto = leerTexto(mensaje);
            if (texto.trim().isEmpty()) {
                System.out.println(ROJO + "Este campo es obligatorio." + RESET);
            }
        } while (texto.trim().isEmpty());
        return texto;
    }

    static void agregarProducto() throws IOException {
        System.out.println(AZUL + "\n--- Agregar nuevo producto ---" + RESET);

        String nombre = leerTextoObligatorio("Nombre: \t");
        float precioCompra = leerFloat("Precio de compra: \t");
        float precioVenta  = leerFloat("Precio de venta: \t");
        int stock = leerEntero("Stock: \t");

        String registro = nombre + ";" + precioCompra + ";" + precioVenta + ";" + stock;

        PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO, true));
        escritor.println(registro);
        escritor.close();

        System.out.println(VERDE + "Producto agregado con éxito." + RESET);
    }

    static void mostrarProductos() throws IOException {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println(ROJO + "No hay productos registrados todavía." + RESET);
            return;
        }

        BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO));
        String linea;

        System.out.println(CYAN + "\n" + String.format("%-20s%-15s%-15s%-10s", "NOMBRE", "P.COMPRA", "P.VENTA", "STOCK") + RESET);
        System.out.println("---------------------------------------------------------");

        while ((linea = lector.readLine()) != null) {
            String[] campos = linea.split(";");
            String nombre = campos[0];
            float pCompra = Float.parseFloat(campos[1]);
            float pVenta  = Float.parseFloat(campos[2]);
            int stock     = Integer.parseInt(campos[3]);

            System.out.println(String.format("%-20s%-15.2f%-15.2f%-10d", nombre, pCompra, pVenta, stock));
        }

        lector.close();
    }

    static void editarProducto() throws IOException {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println(ROJO + "No hay productos para editar." + RESET);
            return;
        }

        String nombreBuscado = leerTexto("Nombre del producto a editar: \t");

        ArrayList<String> registros = new ArrayList<>();
        BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO));
        String linea;
        boolean encontrado = false;

        while ((linea = lector.readLine()) != null) {
            String[] campos = linea.split(";");

            if (campos[0].equalsIgnoreCase(nombreBuscado)) {
                encontrado = true;
                System.out.println(AMARILLO + "Producto encontrado. Ingrese los nuevos datos:" + RESET);

                float nuevoPCompra = leerFloat("Nuevo precio de compra: \t");
                float nuevoPVenta  = leerFloat("Nuevo precio de venta: \t");
                int nuevoStock     = leerEntero("Nuevo stock: \t");

                registros.add(campos[0] + ";" + nuevoPCompra + ";" + nuevoPVenta + ";" + nuevoStock);
            } else {
                registros.add(linea);
            }
        }
        lector.close();

        if (encontrado) {
            PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO));
            for (String r : registros) {
                escritor.println(r);
            }
            escritor.close();
            System.out.println(VERDE + "Producto actualizado con éxito." + RESET);
        } else {
            System.out.println(ROJO + "No se encontró un producto con ese nombre." + RESET);
        }
    }

    static void eliminarProducto() throws IOException {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println(ROJO + "No hay productos para eliminar." + RESET);
            return;
        }

        String nombreBuscado = leerTexto("Nombre del producto a eliminar: \t");

        ArrayList<String> registros = new ArrayList<>();
        BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO));
        String linea;
        boolean encontrado = false;

        while ((linea = lector.readLine()) != null) {
            String[] campos = linea.split(";");
            if (campos[0].equalsIgnoreCase(nombreBuscado)) {
                encontrado = true;
            } else {
                registros.add(linea);
            }
        }
        lector.close();

        PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO));
        for (String r : registros) {
            escritor.println(r);
        }
        escritor.close();

        System.out.println(encontrado ? VERDE + "Producto eliminado." + RESET
                : ROJO + "No se encontró ese producto." + RESET);
    }
}
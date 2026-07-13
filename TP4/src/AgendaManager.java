import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgendaManager {

    private static final Path ARCHIVO_PRINCIPAL = Paths.get("agenda.dat");
    private static final Path ARCHIVO_TEMPORAL  = Paths.get("agenda.dat.tmp");
    private static final String SEPARADOR = ";";

    private final List<Contacto> contactos;

    public AgendaManager() {
        this.contactos = new ArrayList<>();
        cargarDesdeDisco();
    }

    private void cargarDesdeDisco() {
        if (!Files.exists(ARCHIVO_PRINCIPAL)) {
            return;
        }
        try (BufferedReader lector = Files.newBufferedReader(ARCHIVO_PRINCIPAL, StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 0;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) continue;

                String[] partes = linea.split(SEPARADOR, -1);
                if (partes.length < 4) {
                    System.out.println(ConsoleColors.alerta("Línea " + numeroLinea + " del archivo está corrupta y fue omitida."));
                    continue;
                }
                try {
                    String notaPlano = partes[3].isEmpty() ? "" : CryptoUtil.decrypt(partes[3]);
                    contactos.add(new Contacto(partes[0], partes[1], partes[2], notaPlano));
                } catch (Exception e) {
                    System.out.println(ConsoleColors.alerta("No se pudo desencriptar la nota de la línea " + numeroLinea + ". Contacto omitido."));
                }
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.error("No se pudo leer agenda.dat: " + e.getMessage()));
        }
    }

    public boolean guardar() {
        try (BufferedWriter escritor = Files.newBufferedWriter(ARCHIVO_TEMPORAL, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Contacto c : contactos) {
                String notaCifrada = c.getNotaPrivada().isEmpty() ? "" : CryptoUtil.encrypt(c.getNotaPrivada());
                escritor.write(String.join(SEPARADOR, c.getNombre(), c.getTelefono(), c.getEmail(), notaCifrada));
                escritor.newLine();
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.error("Fallo al escribir el archivo temporal: " + e.getMessage()));
            return false;
        }

        try {
            Files.move(ARCHIVO_TEMPORAL, ARCHIVO_PRINCIPAL,
                    StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException noAtomico) {
            try {
                Files.move(ARCHIVO_TEMPORAL, ARCHIVO_PRINCIPAL, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(ConsoleColors.alerta("El sistema de archivos no soporta reemplazo atómico; se usó un reemplazo estándar."));
            } catch (IOException e) {
                System.out.println(ConsoleColors.error("Fallo al reemplazar agenda.dat: " + e.getMessage()));
                return false;
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.error("Fallo al mover el archivo temporal a agenda.dat: " + e.getMessage()));
            return false;
        }
        return true;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void agregar(Contacto c) {
        contactos.add(c);
        guardar();
    }

    public boolean quitarPorNombre(String nombre) {
        Optional<Contacto> encontrado = buscarOpcional(nombre);
        if (encontrado.isPresent()) {
            contactos.remove(encontrado.get());
            guardar();
            return true;
        }
        return false;
    }

    public Contacto buscarPorNombre(String nombre) {
        return buscarOpcional(nombre).orElse(null);
    }

    private Optional<Contacto> buscarOpcional(String nombre) {
        return contactos.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
}
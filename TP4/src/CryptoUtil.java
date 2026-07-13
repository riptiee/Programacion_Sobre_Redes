import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class CryptoUtil {

    private static final String TRANSFORMACION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITMO = "AES";
    private static final int TAMANO_IV = 16; // bytes
    private static final Path ARCHIVO_CLAVE = Paths.get("agenda.key");

    private static final SecretKey CLAVE_SECRETA;

    static {
        try {
            CLAVE_SECRETA = cargarOGenerarClave();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo inicializar el módulo de cifrado AES", e);
        }
    }

    private CryptoUtil() { }

    private static SecretKey cargarOGenerarClave() throws Exception {
        if (Files.exists(ARCHIVO_CLAVE)) {
            String contenido = new String(Files.readAllBytes(ARCHIVO_CLAVE), StandardCharsets.UTF_8).trim();
            byte[] keyBytes = Base64.getDecoder().decode(contenido);
            return new SecretKeySpec(keyBytes, ALGORITMO);
        } else {
            KeyGenerator generador = KeyGenerator.getInstance(ALGORITMO);
            generador.init(128, new SecureRandom());
            SecretKey clave = generador.generateKey();
            String claveB64 = Base64.getEncoder().encodeToString(clave.getEncoded());
            Files.write(ARCHIVO_CLAVE, claveB64.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return clave;
        }
    }

    public static String encrypt(String textoPlano) throws Exception {
        byte[] iv = new byte[TAMANO_IV];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMACION);
        cipher.init(Cipher.ENCRYPT_MODE, CLAVE_SECRETA, new IvParameterSpec(iv));
        byte[] cifrado = cipher.doFinal(textoPlano.getBytes(StandardCharsets.UTF_8));

        byte[] combinado = new byte[iv.length + cifrado.length];
        System.arraycopy(iv, 0, combinado, 0, iv.length);
        System.arraycopy(cifrado, 0, combinado, iv.length, cifrado.length);

        return Base64.getEncoder().encodeToString(combinado);
    }

    public static String decrypt(String textoCifradoB64) throws Exception {
        byte[] combinado = Base64.getDecoder().decode(textoCifradoB64);
        byte[] iv = Arrays.copyOfRange(combinado, 0, TAMANO_IV);
        byte[] cifrado = Arrays.copyOfRange(combinado, TAMANO_IV, combinado.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMACION);
        cipher.init(Cipher.DECRYPT_MODE, CLAVE_SECRETA, new IvParameterSpec(iv));
        byte[] plano = cipher.doFinal(cifrado);

        return new String(plano, StandardCharsets.UTF_8);
    }
}
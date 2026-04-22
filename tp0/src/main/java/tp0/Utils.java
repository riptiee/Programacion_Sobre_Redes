package tp0;

import java.io.*;

public class Utils {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out, true);

    public static String leer() throws IOException {
        return in.readLine();
    }

    public static void mostrar(String txt) {
        out.println(txt);
    }
}
public class ConsoleColors {
    public static final String RESET  = "\u001B[0m";
    public static final String BOLD   = "\u001B[1m";

    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String PURPLE  = "\u001B[35m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";

    public static final String BG_BLUE = "\u001B[44m";

    public static String exito(String msg)  { return GREEN  + "[OK] "     + msg + RESET; }
    public static String error(String msg)  { return RED    + "[ERROR] " + msg + RESET; }
    public static String alerta(String msg) { return YELLOW + "[ALERTA] "+ msg + RESET; }
    public static String info(String msg)   { return CYAN   + msg + RESET; }
}
package tp0;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Guia guia = new Guia();
        int opcion;

        do {
            Utils.mostrar("\n===== MENÚ =====");
            Utils.mostrar("1  - Ejercicio 1");
            Utils.mostrar("2  - Ejercicio 2");
            Utils.mostrar("3  - Ejercicio 3");
            Utils.mostrar("4  - Ejercicio 4");
            Utils.mostrar("5  - Ejercicio 5");
            Utils.mostrar("6  - Ejercicio 6");
            Utils.mostrar("7  - Ejercicio 7");
            Utils.mostrar("8  - Ejercicio 8");
            Utils.mostrar("9  - Ejercicio 9");
            Utils.mostrar("10 - Ejercicio 10");
            Utils.mostrar("11 - Ejercicio 11");
            Utils.mostrar("12 - Ejercicio 12");
            Utils.mostrar("13 - Ejercicio 13");
            Utils.mostrar("14 - Ejercicio 14");
            Utils.mostrar("15 - Ejercicio 15");
            Utils.mostrar("0  - Salir");

            Utils.mostrar("Elegí una opción:");
            opcion = Integer.parseInt(Utils.leer());

            switch(opcion){
                case 1: guia.e1(); break;
                case 2: guia.e2(); break;
                case 3: guia.e3(); break;
                case 4: guia.e4(); break;
                case 5: guia.e5(); break;
                case 6: guia.e6(); break;
                case 7: guia.e7(); break;
                case 8: guia.e8(); break;
                case 9: guia.e9(); break;
                case 10: guia.e10(); break;
                case 11: guia.e11(); break;
                case 12: guia.e12(); break;
                case 13: guia.e13(); break;
                case 14: guia.e14(); break;
                case 15: guia.e15(); break;
                case 0: Utils.mostrar("Programa finalizado"); break;
                default: Utils.mostrar("Opción inválida");
            }

        } while(opcion != 0);
    }
}
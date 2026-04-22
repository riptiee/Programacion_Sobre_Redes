package tp0;

import java.io.*;

public class Guia {

    public void e1() throws IOException {
        Utils.mostrar("Nombre:");
        String t = Utils.leer().toUpperCase();

        String r = "";
        String[] arr = t.split(" ");
        for(int i=0;i<arr.length;i++){
            r += arr[i].charAt(0);
        }

        Utils.mostrar(r);
    }

    public void e2() throws IOException {
        String p = Utils.leer();
        String inv = "";

        for(int i=p.length()-1;i>=0;i--){
            inv += p.charAt(i);
        }

        Utils.mostrar(p.equalsIgnoreCase(inv) ? "SI" : "NO");
    }

    public void e3() throws IOException {
        String t = Utils.leer().toLowerCase();
        int c = 0;

        for(int i=0;i<t.length();i++){
            char ch = t.charAt(i);
            if(ch=='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u') c++;
        }

        Utils.mostrar(""+c);
    }

    public void e4() throws IOException {
        String t = Utils.leer();
        String b = Utils.leer();
        String r = Utils.leer();

        Utils.mostrar(t.replace(b,r));
    }

    public void e5() throws IOException {
        String e = Utils.leer();
        int pos = e.indexOf("@");

        String u = e.substring(0,pos);
        u = u.substring(0,1).toUpperCase()+u.substring(1).toLowerCase();

        Utils.mostrar("Hola "+u);
    }

    public void e6() throws IOException {
        String t = Utils.leer();
        Utils.mostrar(t.trim().replaceAll("\\s+"," "));
    }

    public void e7() throws IOException {
        String p = Utils.leer();

        boolean largo = p.length()>=8;
        boolean num = false;

        for(char c : p.toCharArray()){
            if(Character.isDigit(c)) num = true;
        }

        boolean clave = p.toLowerCase().contains("clave");

        Utils.mostrar(largo && num && !clave ? "OK" : "ERROR");
    }

    public void e8() throws IOException {
        String r = Utils.leer();
        int pos = r.lastIndexOf(".");

        Utils.mostrar(r.substring(pos+1));
    }

    public void e9() throws IOException {
        String t = Utils.leer();
        String res = "";

        for(int i=0;i<t.length();i++){
            char c = t.charAt(i);
            res += (i%2==0)?Character.toUpperCase(c):Character.toLowerCase(c);
        }

        Utils.mostrar(res);
    }

    public void e10() throws IOException {
        String t = Utils.leer();
        String b = Utils.leer();

        int i = t.indexOf(b);
        Utils.mostrar("Ini:"+i+" Fin:"+(i+b.length()));
    }

    public void e11() throws IOException {
        String p = Utils.leer();
        int c = Integer.parseInt(Utils.leer());
        double pr = Double.parseDouble(Utils.leer());

        Utils.mostrar(p+" -> "+(c*pr));
    }

    public void e12() throws IOException {
        String n = Utils.leer();

        StringBuilder sb = new StringBuilder(n);
        sb.insert(0,"Ing. ");

        Utils.mostrar(sb.toString());
    }

    public void e13() throws IOException {
        String t = Utils.leer();
        StringBuilder sb = new StringBuilder(t);

        int i = sb.indexOf("error");
        if(i!=-1) sb.delete(i,i+5);

        Utils.mostrar(sb.toString());
    }

    public void e14() throws IOException {
        String p = Utils.leer();
        Utils.mostrar("<u>"+p+"</u>");
    }

    public void e15() throws IOException {
        String a = Utils.leer();
        String c = Utils.leer();

        Utils.mostrar(a+"-"+c);
    }
}
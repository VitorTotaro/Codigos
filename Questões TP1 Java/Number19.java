import java.util.Scanner;

public class Number19 {
    
    // Função recursiva para remover espaços e substituir A, B e C pelos valores das portas
    private static String substituiValores(String texto, int[] portas, int index) {
        if (index >= texto.length()) return ""; // Caso base

        char c = texto.charAt(index);
        if (c == 'A') return portas[0] + substituiValores(texto, portas, index + 1);
        if (c == 'B') return portas[1] + substituiValores(texto, portas, index + 1);
        if (c == 'C') return portas[2] + substituiValores(texto, portas, index + 1);
        if (c == ' ') return substituiValores(texto, portas, index + 1); // Remove espaços

        return c + substituiValores(texto, portas, index + 1);
    }

    // Função recursiva para aplicar NOT
    private static String not(String texto) {
        int index = texto.indexOf("not(");
        if (index == -1) return texto; // Caso base

        char valor = texto.charAt(index + 4);
        char resultado = (valor == '1') ? '0' : '1';

        return not(texto.substring(0, index) + resultado + texto.substring(index + 6));
    }

    // Função recursiva para aplicar AND
    private static String and(String texto) {
        int index = texto.indexOf("and(");
        if (index == -1) return texto; // Caso base

        int fim = texto.indexOf(")", index);
        String[] valores = texto.substring(index + 4, fim).split(",");
        char resultado = '1';

        for (String v : valores) {
            if (v.charAt(0) == '0') {
                resultado = '0';
                break;
            }
        }

        return and(texto.substring(0, index) + resultado + texto.substring(fim + 1));
    }

    // Função recursiva para aplicar OR
    private static String or(String texto) {
        int index = texto.indexOf("or(");
        if (index == -1) return texto; // Caso base

        int fim = texto.indexOf(")", index);
        String[] valores = texto.substring(index + 3, fim).split(",");
        char resultado = '0';

        for (String v : valores) {
            if (v.charAt(0) == '1') {
                resultado = '1';
                break;
            }
        }

        return or(texto.substring(0, index) + resultado + texto.substring(fim + 1));
    }

    // Função recursiva para resolver expressões booleanas
    private static String boolAlgebra(String texto) {
        if (!texto.contains("(")) return texto; // Caso base: sem operações restantes

        texto = and(texto);
        texto = or(texto);
        texto = not(texto);

        return boolAlgebra(texto);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int qtdePortas = scanner.nextInt();
        int[] portas = new int[3];

        while (qtdePortas != 0) {
            for (int i = 0; i < qtdePortas; i++)
                portas[i] = scanner.nextInt();
            scanner.nextLine();
            String texto = scanner.nextLine();

            // Substituir valores
            texto = substituiValores(texto, portas, 0);

            // Resolver a expressão booleana
            texto = boolAlgebra(texto);

            System.out.println(texto);
            qtdePortas = scanner.nextInt();
        }
        
        scanner.close();
    }
}

import java.util.Scanner;

public class Lab6 {
    public static void main(String[] args) throws Exception {

        PilhaLinear pilha = new PilhaLinear(100);

        Scanner scanner = new Scanner(System.in);

        char letra;
        int numero;

        while (scanner.hasNext()) {
            letra = scanner.next().charAt(0);
            if (letra == 'E') {
                numero = scanner.nextInt();
                pilha.empilhar(numero);
            }
            else if (letra == 'M') {
                pilha.mostrar();
            }
            else if (letra == 'D') {
                System.out.printf("%d\n", pilha.desempilhar());
            }
            else if (letra == 'P') {
                numero = scanner.nextInt();
                if (pilha.pesquisar(numero)) {
                    System.out.println("S");
                } else {
                    System.out.println("N");
                }
            }
        }
        scanner.close();
    }

}
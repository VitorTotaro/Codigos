import java.util.Scanner;

public class Fila {
    int[] array;
    int primeiro, ultimo;

    Fila() {
        this(5);
    }

    Fila(int tamanho) {
        array = new int[tamanho + 1];
        primeiro = ultimo = 0;
    }

    void inserir(int elem) throws Exception {
        if (primeiro == (ultimo + 1) % array.length) {
            throw new Exception("=====LISTA CHEIA=====");

        } else {
            array[ultimo] = elem;
            ultimo = (ultimo + 1) % array.length;
        }
    }

    int remover() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("=====LISTA VAZIA=====");
        }
        int resp = array[primeiro];
        primeiro = (primeiro + 1) % array.length;

        return resp;
    }

    void mostrar() {
        if (primeiro == ultimo) {
            System.out.println("=====LISTA VAZIA!=====");
        } else {
            for (int i = primeiro; i != ultimo; i = (i + 1) % array.length) {
                System.out.printf("%d ", array[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o tamanho da fila: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        Fila fila = new Fila(tamanho);

        while (true) {
            System.out.print("Digite um comando (inserir X / remover / mostrar / sair): ");
            String comando = scanner.nextLine();

            if (comando.startsWith("inserir ")) {
                try {
                    int numero = Integer.parseInt(comando.split(" ")[1]);
                    fila.inserir(numero);
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } else if (comando.equals("remover")) {
                System.out.printf("%d", fila.remover());
                System.out.println();
            } else if (comando.equals("mostrar")) {
                fila.mostrar();
            } else if (comando.equals("sair")) {
                break;
            } else {
                System.out.println("Comando inválido. Use: inserir X, remover, mostrar ou sair.");
            }
        }

        scanner.close();
    }
}

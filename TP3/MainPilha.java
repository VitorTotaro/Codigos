import java.io.File;
import java.util.Scanner;

public class MainPilha {
    public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
    Scanner in = new Scanner(System.in);

    Show[] shows = new Show[1368];
    sc.nextLine(); // pular cabeçalho

    for (int i = 0; i < 1368; i++) {
        shows[i] = Show.ler(sc.nextLine());
    }

    PilhaFlexivel pilha = new PilhaFlexivel();
    String entrada = in.nextLine();

    
    while (!entrada.equals("FIM")) {
        int index = Integer.parseInt(entrada.substring(1)) - 1;
        pilha.empilhar(shows[index]);
        entrada = in.nextLine();
    }

    int contador = Integer.parseInt(in.nextLine());

    for (int i = 0; i < contador; i++) {
        entrada = in.next();
        switch (entrada) {
            case "I":
                entrada = in.next();
                pilha.empilhar(shows[Integer.parseInt(entrada.substring(1)) - 1]);
                break;
            case "R":
                System.out.println("(R) " + pilha.remover().title);
                break;
        }
    }

    pilha.mostrar();

    sc.close();
    in.close();
}
}


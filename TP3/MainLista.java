import java.io.File;
import java.util.Scanner;

public class MainLista {
public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(new File("./disneyplus.csv"));
    Scanner in = new Scanner(System.in);

    Show[] shows = new Show[1368];
    sc.nextLine(); // pular cabeçalho

    for (int i = 0; i < 1368; i++) {
        shows[i] = Show.ler(sc.nextLine());
    }

    Lista lista = new Lista(300);
    String entrada = in.nextLine();

    
    while (!entrada.equals("FIM")) {
        int index = Integer.parseInt(entrada.substring(1)) - 1;
        lista.inserirFim(shows[index]);
        entrada = in.nextLine();
    }

    int contador = Integer.parseInt(in.nextLine());

    for (int i = 0; i < contador; i++) {
        entrada = in.next();
        switch (entrada) {
            case "II":
                entrada = in.next();
                lista.inserirInicio(shows[Integer.parseInt(entrada.substring(1)) - 1]);
                break;
            case "IF":
                entrada = in.next();
                lista.inserirFim(shows[Integer.parseInt(entrada.substring(1)) - 1]);
                break;
            case "I*":
                int pos = in.nextInt();
                entrada = in.next();
                lista.inserir(shows[Integer.parseInt(entrada.substring(1)) - 1], pos);
                break;
            case "RI":
                System.out.println("(R) " + lista.removerInicio().title);
                break;
            case "RF":
                System.out.println("(R) " + lista.removerFim().title);
                break;
            case "R*":
                int p = in.nextInt();
                System.out.println("(R) " + lista.remover(p).title);
                break;
        }
    }

    lista.mostrar();

    sc.close();
    in.close();
}

}
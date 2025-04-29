import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Number3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
        Scanner in = new Scanner(System.in);

        Show[] shows = new Show[1368];
        sc.nextLine(); // pular cabeçalho

        for (int i = 0; i < 1368; i++) {
            shows[i] = Show.ler(sc.nextLine());
        }

        Show[] sequencial = new Show[300];

        String entrada = in.next();
        for (int i = 0; !entrada.equals("FIM"); i++) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            sequencial[i] = shows[index];
            entrada = in.next();
        }

        entrada = in.nextLine();
        while (!entrada.equals("FIM")) {
            for (int j = 0; j < sequencial.length; j++) {
                if (entrada.equals(sequencial[j].title)) {
                    System.out.println("SIM");
                    j = 300;
                } else if (j == sequencial.length - 1) {
                    System.out.println("NAO");
                }
            }
            entrada = in.nextLine();
        }
        sc.close();
        in.close();
    }
}


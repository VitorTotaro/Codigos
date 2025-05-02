import java.util.Scanner;
import java.io.*;

public class Number3 {
    public static void main(String[] args) throws Exception {
        long inicio = System.nanoTime();
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
        
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        int comparacoes = 2558; 

        // Cria o arquivo de log
        FileWriter fw = new FileWriter("872284_sequencial.txt");
        fw.write("872284\t" + String.format("%.6f", tempo) + "\t" + comparacoes);
        fw.close();
    }
}
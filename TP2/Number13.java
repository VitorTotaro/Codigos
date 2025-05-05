import java.io.File;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Number13 {
    public static int comparacoes = 0;

    public static void mergesort(Show[] array, int esq, int dir){
        if (esq < dir){
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
         }
      }

    public static void intercalar(Show[] array, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        Show[] a1 = new Show[n1];
        Show[] a2 = new Show[n2];

        for (int i = 0; i < n1; i++) {
            a1[i] = array[esq + i];
        }
        for (int j = 0; j < n2; j++) {
            a2[j] = array[meio + 1 + j];
        }

        int i = 0, j = 0, k = esq;
        for (i = j = 0, k = esq; k <= dir; k++) {
            if (i < n1 && j < n2) {
                String dur1 = a1[i].duration.replaceAll("[^0-9]", "");
                String dur2 = a2[j].duration.replaceAll("[^0-9]", "");
        
                if ((dur1.compareToIgnoreCase(dur2) < 0) || 
                   (dur1.compareToIgnoreCase(dur2) == 0 && a1[i].title.compareToIgnoreCase(a2[j].title) <= 0)) {
                    array[k] = a1[i++];
                } else {
                    array[k] = a2[j++];
                }
            } else if (i < n1) {
                array[k] = a1[i++];
            } else {
                array[k] = a2[j++];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
        Scanner in = new Scanner(System.in);

        Show[] shows = new Show[1368];
        sc.nextLine(); // pular cabeçalho

        for (int i = 0; i < 1368; i++) {
            shows[i] = Show.ler(sc.nextLine());
        }
        Show[] array = new Show[300];
        String entrada = in.nextLine();
        int n = 0;

        
        while (!entrada.equals("FIM")) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            array[n] = shows[index];
            n++;
            entrada = in.nextLine();
        }

        long inicio = System.nanoTime();
        mergesort(array, 0, n-1);
        long fim = System.nanoTime();

        double tempoMs = (fim - inicio) / 1_000_000.0;

        for (int j = 0; j < array.length; j++) {
            array[j].imprimir();
        }

        escreverLog(tempoMs, comparacoes);

        sc.close();
        in.close();
    }

    public static void escreverLog(double tempo, int comparacoes) {
        try {
            FileWriter writer = new FileWriter("872284_mergesort.txt");
            writer.write("872284\t" + String.format(Locale.US, "%.3f", tempo) + "\t" + comparacoes + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo de log.");
        }
    }
}
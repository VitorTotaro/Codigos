import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Number9 {
    static int comparacoes = 0;
    public static void heapSort(Show[] array, int n) {
        construirHeap(array, n);

        for (int i = n - 1; i > 0; i--) {
            Show temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    public static void construirHeap(Show[] array, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
    }

    public static void heapify(Show[] array, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < n && comparar(array[esq], array[maior]) > 0) {
            maior = esq;
        }

        if (dir < n && comparar(array[dir], array[maior]) > 0) {
            maior = dir;
        }

        if (maior != i) {
            Show temp = array[i];
            array[i] = array[maior];
            array[maior] = temp;

            heapify(array, n, maior);
        }
    }

    public static int comparar(Show a, Show b) {
        comparacoes++;
        int res = a.director.trim().compareToIgnoreCase(b.director.trim());
        if (res == 0) {
            return a.title.trim().compareToIgnoreCase(b.title.trim());
        }
        return res;
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
        heapSort(array, n);
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
            FileWriter writer = new FileWriter("872284_heapsort.txt");
            writer.write("872284\t" + String.format(Locale.US, "%.3f", tempo) + "\t" + comparacoes + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo de log.");
        }
    }
}

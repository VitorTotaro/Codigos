import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Number11 {
     @SuppressWarnings("unchecked")
    public static void countingsort(Show[] array) {
        int n = array.length;
        int maior = getMaior(array);
        int menor = getMenor(array);
        int range = maior - menor + 1;

        // Criar um vetor de listas
        ArrayList<Show>[] buckets = new ArrayList[range];
        for (int i = 0; i < range; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Distribuir objetos nos buckets baseados no release
        for (int i = 0; i < n; i++) {
            buckets[array[i].release - menor].add(array[i]);
        }

        // Agora ordenar dentro de cada bucket pelo title
        for (ArrayList<Show> bucket : buckets) {
            bucket.sort(Comparator.comparing(show -> show.title));
        }

        // Preencher o array ordenado
        int index = 0;
        for (ArrayList<Show> bucket : buckets) {
            for (Show show : bucket) {
                array[index++] = show;
            }
        }
    }

    public static int getMaior(Show[] array) {
        int maior = array[0].release;
        for (int i = 1; i < array.length; i++) {
            if (array[i].release > maior) {
                maior = array[i].release;
            }
        }
        return maior;
    }

    public static int getMenor(Show[] array) {
        int menor = array[0].release;
        for (int i = 1; i < array.length; i++) {
            if (array[i].release < menor) {
                menor = array[i].release;
            }
        }
        return menor;
    }


    
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
        Scanner in = new Scanner(System.in);

        Show[] shows = new Show[1368];
        sc.nextLine(); // pular cabeçalho

        for (int i = 0; i < 1368; i++) {
            shows[i] = Show.ler(sc.nextLine());
        }
        Show[] array = new Show[300];
        String entrada = in.nextLine();

        int i = 0;
        while (!entrada.equals("FIM")) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            array[i] = shows[index];
            i++;
            entrada = in.nextLine();
        }

        countingsort(array);

        for (int j = 0; j < array.length; j++) {
            array[j].imprimir();
        }

        sc.close();
        in.close();
    }
}

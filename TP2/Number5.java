import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Number5 {
    public static Show[] selectionSort(Show[] array) {
        for (int i = 0; i < array.length; i++) {
            int menor = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].gettitle().compareToIgnoreCase(array[menor].gettitle()) < 0) {
                    menor = j;
                }
            }
            Show tmp = array[i];
            array[i] = array[menor];
            array[menor] = tmp;

        }
        return array;
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
        while (in.hasNextLine() || !entrada.equals("FIM")) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            array[i] = shows[index];
            i++;
            entrada = in.nextLine();
        }

        array = selectionSort(array);

        for (int j = 0; j < array.length; j++) {
            array[j].imprimir();
        }
        sc.close();
        in.close();
    }
}


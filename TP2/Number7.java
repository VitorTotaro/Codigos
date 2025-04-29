import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Number7 {
     public static Show[] insertionSort(Show[] array) {
        for (int i = 1; i < array.length; i++) {
            Show temp = array[i];
            int j = i - 1;
    
            // Enquanto o elemento anterior for "maior" moverá uma posição à frente
            while (j >= 0 && (
                    array[j].type.compareToIgnoreCase(temp.type) > 0 ||
                    (array[j].type.compareToIgnoreCase(temp.type) == 0 &&
                     array[j].title.compareToIgnoreCase(temp.title) > 0)
                )) {
                array[j + 1] = array[j];
                j--;
            }
    
            array[j + 1] = temp;
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
        while (!entrada.equals("FIM")) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            array[i] = shows[index];
            i++;
            entrada = in.nextLine();
        }

        array = insertionSort(array);

        for (int j = 0; j < array.length; j++) {
            array[j].imprimir();
        }

        sc.close();
        in.close();
    }
}

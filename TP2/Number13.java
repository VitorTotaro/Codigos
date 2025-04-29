import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Number13 {
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
                int dur1 = Integer.parseInt(a1[i].duration.replaceAll("[^0-9]", ""));
                int dur2 = Integer.parseInt(a2[j].duration.replaceAll("[^0-9]", ""));
        
                if (dur1 < dur2 || 
                   (dur1 == dur2 && a1[i].title.compareToIgnoreCase(a2[j].title) <= 0)) {
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
        
        mergesort(array, 0, i-1);

        for (int j = 0; j < array.length; j++) {
            array[j].imprimir();
        }

        sc.close();
        in.close();
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Number18 {
    public static void quicksort(Show[] array, int esq, int dir) {
        int i = esq, j = dir;
        Show.Data pivo = array[(esq + dir) / 2].data;
        String tituloPivo = array[(esq + dir) / 2].title;

        while (i <= j) {
            while (
                array[i].data.ano < pivo.ano ||
                (array[i].data.ano == pivo.ano && array[i].data.mes < pivo.mes) ||
                (array[i].data.ano == pivo.ano && array[i].data.mes == pivo.mes && array[i].data.dia < pivo.dia) ||
                (array[i].data.ano == pivo.ano && array[i].data.mes == pivo.mes && array[i].data.dia == pivo.dia &&
                 array[i].title.trim().compareToIgnoreCase(tituloPivo.trim()) < 0)
            ) {
                i++;
            }
    
            while (
                array[j].data.ano > pivo.ano ||
                (array[j].data.ano == pivo.ano && array[j].data.mes > pivo.mes) ||
                (array[j].data.ano == pivo.ano && array[j].data.mes == pivo.mes && array[j].data.dia > pivo.dia) ||
                (array[j].data.ano == pivo.ano && array[j].data.mes == pivo.mes && array[j].data.dia == pivo.dia &&
                 array[j].title.trim().compareToIgnoreCase(tituloPivo.trim()) > 0)
            ) {
                j--;
            }
    
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j) quicksort(array, esq, j);
        if (i < dir) quicksort(array, i, dir);
    }

    public static void swap(Show[] array, int i, int j) {
        Show temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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

       quicksort(array, 0, array.length-1);

        for (int j = 0; j < 10; j++) {
            array[j].imprimir();
        }

        
        sc.close();
        in.close();
    }
}

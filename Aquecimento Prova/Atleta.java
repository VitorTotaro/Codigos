import java.util.Scanner;

public class Atleta {
    public static class Atletas {
        String nome;
        int peso;
    }

    public static void ordenar(Atletas[] array) {
        for (int i = 0; i < array.length; i++) {
            int maior = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].peso > array[maior].peso || (array[j].peso == array[maior].peso &&
                        array[j].nome.compareToIgnoreCase(array[maior].nome) < 0)) {
                    maior = j;
                }
            }
            Atletas tmp = array[i];
            array[i] = array[maior];
            array[maior] = tmp;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        Atletas[] array = new Atletas[n];
        
        // Preencher o array com n atletas
        for (int i = 0; i < n; i++) {
            array[i] = new Atletas();
            array[i].nome = sc.next();
            array[i].peso = sc.nextInt();
        }

        ordenar(array);

        // Imprimir os atletas ordenados
        for (int i = 0; i < n; i++) {
            System.out.println(array[i].nome + " " + array[i].peso);
        }
        
        sc.close();
    }
}
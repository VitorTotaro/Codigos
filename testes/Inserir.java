import java.util.Scanner;
class Inserir{
    public static int[] array;
    public static int n = 0;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("qual o tamanho do array?");
        int b = sc.nextInt();
        sc.nextLine();
        array = new int[b];//definindo tamanho do array

        System.out.println("digite os elementos:");
        String linha = sc.nextLine();
        
        Scanner scannerLinha = new Scanner(linha);
        
        while(scannerLinha.hasNextInt()){
            int numero = scannerLinha.nextInt();
            if(n<array.length)
                array[n] = numero;
                n++;
        }

        //mostrar array atual
        System.out.printf("Array inicial: [");
        for(int i = 0; i < n; i++){
            System.out.printf("%d ", array[i]);  
        }
        System.out.printf("]");

        System.out.println();


        
        //mostrar array ordenado
        System.out.printf("Array ordenado: [");
        inserir();
        for(int i = 0; i < n; i++){
            System.out.printf("%d ", array[i]);  
        }
        System.out.printf("]");

    sc.close();
    scannerLinha.close();
    } 

    public static void inserir(){
        for(int i=1; i<n; i++){
            int temp = array[i];
            int j = (i-1); 
            while(j>=0 && array[j]>temp){
                array[j+1] = array[j];
                j--;
        }
        array[j+1] = temp;
        }
    }
    }

import java.util.Scanner;

class Espelho{

    public static void mostrarEspelho(int a, int b){

        int c = b - a;
        String espelho = "";
        for(int i = 0; i<=c; i++){
            System.out.printf("%d", a+i);
            espelho += (a+i);
        }

        int n = espelho.length() - 1;
        for(int i = 0; i<=n; i++){
            System.out.printf("%s", espelho.charAt(n-i));
        }
        System.out.printf("\n");
    }

    public static void main(String[] args){
        int a,b; //numero inicial e final
        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()){
            a = sc.nextInt();
            b = sc.nextInt();
            mostrarEspelho(a,b);
        }
        sc.close();
    }
}
import java.util.Scanner;

public class Number7{
    public static String inverter(String frase){
       char [] novaFrase = new char[frase.length()];
       int n = frase.length();

       for(int i=0; i<n; i++){
        novaFrase[i] = frase.charAt(n -i -1);
       }

       String invertida = new String(novaFrase);

       return invertida;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        String frase = scanner.nextLine();
        

        while (!(frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M')){
            
            System.out.println(inverter(frase));
            frase = scanner.nextLine();
        }
        
        scanner.close();
       
    }
}
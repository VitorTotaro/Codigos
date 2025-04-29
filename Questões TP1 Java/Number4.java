import java.util.Scanner;
import java.util.Random;

public class Number4{
    public static String subistituir(String frase, char primeiraLetra, char segundaLetra){

        char[] novaFrase = new char[frase.length()];

        for(int i=0; i<frase.length(); i++){
            if(frase.charAt(i) == primeiraLetra){
                novaFrase[i] = segundaLetra;
            }
            else{
                novaFrase[i] = frase.charAt(i);
            }
            /*if(i == frase.length()-1){
                if(conferir == 0){
                    primeiraLetra = (char)( 'a' + (Math.abs(generator.nextInt()) % 26));
                    segundaLetra = (char)( 'a' + (Math.abs(generator.nextInt()) % 26));
                    i=0;
                }
            }*/
        }
        
  return new String(novaFrase);

    }



    public static void main (String [] args){
        
        Scanner scanner = new Scanner (System.in);

        Random generator = new Random();
        generator.setSeed(4);

        char primeiraLetra, segundaLetra;

        String frase = scanner.nextLine();

        while (!(frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M')){
        
            primeiraLetra = ((char)( 'a' + (Math.abs(generator.nextInt()) % 26)));
            segundaLetra = ((char)( 'a' + (Math.abs(generator.nextInt()) % 26)));


            System.out.println(subistituir(frase, primeiraLetra, segundaLetra));
            frase = scanner.nextLine();
        }
        
        scanner.close();
}
}
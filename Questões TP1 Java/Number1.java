import java.util.Scanner;

public class Number1{

public static boolean isPalindromo(String frase){
    boolean resp = true;
    int i=0;
    int j=frase.length() -1;

   while(i<j) {
       if (frase.charAt(i) != frase.charAt(j)){
         resp = false;
         i = j;
       }
       else{
        j--;
        i++;
       }
    }

    return resp;
}
    public static void main (String [] args){
        Scanner scanner= new Scanner(System.in);
        
        
        String frase = scanner.nextLine();

        while (!frase.equals("FIM")){
            if(isPalindromo(frase)==true){
                System.out.println("SIM");
            }
            else{
                System.out.println("NAO");
            }
            frase = scanner.nextLine();
        }

    

    scanner.close();
    
       
    }
}
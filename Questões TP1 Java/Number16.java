import java.util.Scanner;

public class Number16{

public static boolean isPalindromo(String frase) {
    return verificarPalindromo(frase, 0, frase.length() - 1);
}

private static boolean verificarPalindromo(String frase, int i, int j) {
    if (i >= j) {
        return true; 
    }
    if (frase.charAt(i) != frase.charAt(j)) {
        return false; 
    }
    return verificarPalindromo(frase, i + 1, j - 1); 
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
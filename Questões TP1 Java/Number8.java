import java.util.Scanner;

public class Number8{
    public static int somarDigitos(String digitos){
       
       int soma = 0;

       for(int i = 0; i < digitos.length(); i++) {
        // Converte cada caractere para seu valor numérico
        soma += Character.getNumericValue(digitos.charAt(i));
    }
    return soma;
    }

public static void main (String[] args){
    Scanner scanner = new Scanner(System.in);
    
    String entrada = scanner.nextLine();

    while (!(entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')){
    System.out.println(somarDigitos(entrada));
    entrada = scanner.nextLine();
    }

    scanner.close();

}

}
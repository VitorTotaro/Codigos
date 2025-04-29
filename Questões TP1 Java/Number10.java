import java.util.Scanner;

public class Number10{
    public static int contarPalavras(String frase){

    int contador = 0;
    for(int i=0; i<frase.length(); i++){
        if(frase.charAt(i) == ' '){
            contador++;                   //conta o número de espaços que existe na string
    }
    }

    contador++;  //adiciona mais um, pois o numero de espaços + 1 é igual ao número de palavras

    return contador;

    }

public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);

    String frase = scanner.nextLine();

    while(!frase.equals("FIM")){
        System.out.println(contarPalavras(frase));
        frase = scanner.nextLine();
    }
}
}
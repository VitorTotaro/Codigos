/*import java.util.Scanner;

public class Number3{
    public static String criptografar(String frase){

        char letra, novoChar;  //letra atual e nova letra após criptografia
        char[] caracteres = new char [frase.length()];
        String especiais = "áéíóúâêôàèìòùçÁÉÍÓÚÂÊÔÀÈÌÒÙÇ"; //para lidar com caracteres acentuados e ç

        for(int i=0; i<frase.length(); i++){

            // lidar com caracteres maiúsculos
            if(frase.charAt(i) >= 'A' && frase.charAt(i)<= 'Z'){
                letra = frase.charAt(i);                              
                novoChar = (char)('A' + (letra -'A' + 3)%26);
                caracteres[i] = novoChar;
          }
            //lidar com caracteres minúsculos
            else if(frase.charAt(i) >= 'a' && frase.charAt(i)<= 'z'){
                letra = frase.charAt(i);
                novoChar = (char)('a'+ (letra - 'a' +3)%26);
                caracteres[i] = novoChar;
            }
            
            //lidar com caracteres especiais
            else if (especiais.indexOf(frase.charAt(i)) != -1) {
                caracteres[i] = frase.charAt(i);
           }

            //lidar com números, caracteres especiais e etc
            else{
            letra = frase.charAt(i);
            novoChar = (char)(letra + 3);
            caracteres[i] = novoChar;
            }
        }
    
    //cria frase criptografada
    String novaFrase = new String(caracteres);

    return novaFrase;
    }
    

    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);

        String frase = scanner.nextLine();

        while (!(frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M')){
            System.out.println(criptografar(frase));
            frase = scanner.nextLine();
        }


        scanner.close();
    }
}*/
import java.util.Scanner;

public class Number3{
    private static final int SHIFT = 3;
    private static final char REPLACEMENT_CHAR = '\uFFFD';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            String encryptedMessage = cifraCesar(input, SHIFT);
            System.out.println( encryptedMessage);
        }

        scanner.close();
    }

    public static String cifraCesar(String str, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c == REPLACEMENT_CHAR) {
                result.append(c);
            } else {
                char encryptedChar = (char) (c + shift);
                result.append(encryptedChar);
            }
        }

        return result.toString();
    }
}
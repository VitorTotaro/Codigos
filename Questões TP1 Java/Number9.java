import java.util.Scanner;
import java.util.Arrays;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Number9{
    public static String[] normalizar(String str1, String str2) {
        // Converter para minúsculas
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        // Normalizar os caracteres Unicode para decomposição de acentos
        str1 = Normalizer.normalize(str1, Normalizer.Form.NFD);
        str2 = Normalizer.normalize(str2, Normalizer.Form.NFD);

        // Remover os caracteres diacríticos (acentos)
        str1 = str1.replaceAll("\\p{M}", "");
        str2 = str2.replaceAll("\\p{M}", "");
        
        return new String[]{str1, str2};
    }




    public static boolean isAnagrama(String palavra, String palavraA){
        if(palavra.length() != palavraA.length())
            return false;

    String[] normalizadas = normalizar(palavra,palavraA); //normalizar strings

    String palavra1 = normalizadas[0];
    String palavra2 = normalizadas[1];
    
    char[]array1 = palavra1.toCharArray();
    char[]array2 = palavra2.toCharArray();

    Arrays.sort(array1);
    Arrays.sort(array2);
    
    return Arrays.equals(array1,array2);
    }

public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);

    String linha = scanner.nextLine();
  

    while (!linha.equals("FIM")) {
            String[] partes = linha.split("-", 2);
                String palavra1 = partes[0];
                String palavra2 = partes[1];
                
                if (isAnagrama(palavra1, palavra2)) {
                    System.out.println("SIM");
                } else {
                    System.out.println("N�O");
                }
            
            linha = scanner.nextLine();
        }
    
    scanner.close();
}
}
/*import java.util.Scanner;
import java.util.Arrays;

public class Number9 {
    
    public static String normalizar(String str) {
        StringBuilder resultado = new StringBuilder();
        
        // Converte para minúsculas
        str = str.toLowerCase();
        
        // Processa cada caractere
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            // Letras básicas a-z
            if (c >= 'a' && c <= 'z') {
                resultado.append(c);
            }
            // Caractere de substituição (65533) - tenta determinar com base na posição e contexto
            else if (c == 65533) {
                // Ignora completamente, não tentamos adivinhar
                continue;
            }
            // Ignora outros caracteres (pontuação, números, etc.)
        }
        
        return resultado.toString();
    }
    
    public static boolean isAnagrama(String palavra1, String palavra2) {
        // Normaliza (remove acentos, caracteres especiais e mantém só letras a-z)
        String p1Normalizada = normalizar(palavra1);
        String p2Normalizada = normalizar(palavra2);
        
        // Verificação especial para detectar padrões em strings que contêm '?'
        if (palavra1.contains("?") || palavra2.contains("?")) {
            // Se uma tem '?' e as duas palavras têm o mesmo comprimento após normalização
            // e uma é anagrama da outra considerando apenas as letras básicas,
            // então provavelmente são anagramas com caracteres especiais
            if (p1Normalizada.length() == p2Normalizada.length()) {
                // Ordenar letras para verificar se são anagramas
                char[] arr1 = p1Normalizada.toCharArray();
                char[] arr2 = p2Normalizada.toCharArray();
                Arrays.sort(arr1);
                Arrays.sort(arr2);
                
                // Se as letras básicas são as mesmas, consideramos anagramas
                if (Arrays.equals(arr1, arr2)) {
                    return true;
                }
            }
        }
        
        // Verificação padrão para anagramas (após normalização)
        if (p1Normalizada.length() != p2Normalizada.length()) {
            return false;
        }
        
        // Contagem de caracteres usando array (mais eficiente que HashMap)
        int[] contagem = new int[26]; // a-z
        
        for (char c : p1Normalizada.toCharArray()) {
            contagem[c - 'a']++;
        }
        
        for (char c : p2Normalizada.toCharArray()) {
            if (--contagem[c - 'a'] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String linha = scanner.nextLine();
        
        while (!linha.equals("FIM")) {
            String[] partes = linha.split("-", 2);
            if (partes.length < 2) {
                System.out.println("Entrada inválida. Use o formato: palavra1-palavra2");
            } else {
                String palavra1 = partes[0].trim();
                String palavra2 = partes[1].trim();
                
                if (isAnagrama(palavra1, palavra2)) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NÃO");
                }
            }
            linha = scanner.nextLine();
        }
        scanner.close();
    }
}*/



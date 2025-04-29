import java.util.Scanner;

public class Number18 {
    private static final int SHIFT = 3;
    private static final char REPLACEMENT_CHAR = '\uFFFD';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            String encryptedMessage = cifraCesar(input, SHIFT, 0);
            System.out.println(encryptedMessage);
        }

        scanner.close();
    }

    public static String cifraCesar(String str, int shift, int index) {
        if (index >= str.length()) {
            return "";
        }

        char c = str.charAt(index);
        char encryptedChar = (c == REPLACEMENT_CHAR) ? c : (char) (c + shift);
        
        return encryptedChar + cifraCesar(str, shift, index + 1);
    }
}

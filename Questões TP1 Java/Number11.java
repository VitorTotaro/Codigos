import java.util.*;

public class Number11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputs = new ArrayList<>();
        
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("FIM")) break;
            inputs.add(line);
        }

        scanner.close();

        for (int i = 0; i < inputs.size(); i++) {
            int length = longestUniqueSubstring(inputs.get(i));
            System.out.println(length);
        }
    }

    public static int longestUniqueSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        Set<Character> set = new HashSet<>();
        int maxLength = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    private static String reverse(String line) {
        if (line.length() >= 1 && line.length() <= 2000 && line.matches("[a-z]*")) {
            Matcher m = Pattern.compile("\\((.*?)\\)").matcher(line);
            while (m.find()) {
                String withinParenthesis = m.group(1);
                String reversed = new StringBuilder(withinParenthesis).reverse().toString();
                line = line.replace(m.group(0), "(" + reversed + ")");
            }
        }

        return line;
    }

    public static void main(String[] args) {
        System.out.println("Input:");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println("Output:" + reverse(line));
    }
}

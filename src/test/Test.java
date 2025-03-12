package test;

import components.Lexer;
import components.Parser;

public class Test {
    public static void testCases() {
        // Number arithmetic tests
        System.out.println("3 + 4 = " + evaluateNumber("3 + 4")); // 7
        System.out.println("10 - 2 = " + evaluateNumber("10 - 2")); // 8
        System.out.println("5 * 2 = " + evaluateNumber("5 * 2")); //10
        System.out.println("5 / 2 = " + evaluateNumber("10 / 2")); //2


        // Word concatenation tests
        System.out.println("\"Hello\" + \" World\" = " + evaluateString("\"Hello\" + \" World\"")); // "Hello World"
        System.out.println("\"Test\" * 3 = " + evaluateString("\"Test\" * 3")); // Error: [Cannot multiply or divide strings]

        // Error handling
        try {
            System.out.println(evaluateNumber("\"Oops\" + 5")); // Error
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static int evaluateNumber(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        return parser.exprAsNumber();
    }

    public static String evaluateString(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        return parser.exprAsWord();
    }
}

package compiler;
import components.Lexer;
import components.Parser;
import components.Token;
import java.util.Scanner;
public class CodewareCompiler {
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();

    Lexer lexer = new Lexer(input);
    Parser parser = new Parser(lexer);
}

import AST.*;
import compiler.CodewareCompiler;


public class Main {
    public static void main(String[] args) {

        String testInput = "NUMBER a = 10.5; NUMBER b = 10.2; a + b; ";

        new CodewareCompiler(testInput);

    }

}

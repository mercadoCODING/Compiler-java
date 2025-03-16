import AST.*;
import compiler.CodewareCompiler;


public class Main {
    public static void main(String[] args) {

        String testInput = "NUMBER a = 1; LOOP a TO 5 { WORD msg = \"Counting\"; a = a + 1; }";

        new CodewareCompiler(testInput);

    }

}

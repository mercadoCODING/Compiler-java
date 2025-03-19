import AST.*;
import compiler.CodewareCompiler;


public class Main {
    public static void main(String[] args) {

        String testInput = "PRINT(INPUT(\"Enter your name:\"));"; //check
        String testInput1 = "PRINT(\"Hello World\");"; //check
        String testInput4 = "NUMBER x = 5; NUMBER y = 10; PRINT(x + y);"; //check
        String testInput6 = "WORD username; INPUT(username); PRINT(\"Hello, \" + username);"; //check
        String testInput7 = "WORD msg = \"Hello\";INPUT(msg);PRINT(msg);";//check
        String testInputFunc = "FUNC sayHello(){ PRINT(\"Hello\"); } sayHello();";

        new CodewareCompiler(testInputFunc);

    }

}

import AST.*;
import compiler.CodewareCompiler;


public class Main {
    public static void main(String[] args) {

        String testInput = "PRINT(INPUT(\"Enter your name:\"));"; //verified
        String testInput1 = "PRINT(\"Hello World\");"; //verified
        String testInput2 = "NUMBER x = 5; NUMBER y = 10;NUMBER z = 10; PRINT(x + y + z);"; //verified
        String testInput3 = "WORD username; INPUT(username); PRINT(\"Hello, \" + username);"; //verified!
        String testInput4 = "WORD msg = \"Hello\";INPUT(msg);PRINT(msg);"; //verified behavior dapat hindi mag eexecute ung print
        String testInput5 = "FUNC sayHello(){PRINT(\"Hello\");} sayHello();"; //verified
        String testInput6 = "FUNC add(a, b) { RETURN a + b; } PRINT(add(5, 10));";//verified

        new CodewareCompiler(testInput6);

    }

}

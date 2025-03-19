package AST;

import modules.Statement;

import java.util.Map;
import java.util.Scanner;

public class InputStatement implements Statement {
    private final String variableName;

    public InputStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        //System.out.print("Enter value for " + variableName + ": ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        symbolTable.put(variableName, input);
    }
}

package AST;

import modules.Expression;

import java.util.Map;
import java.util.Scanner;

public class InputExpression implements Expression {
    private final String prompt;

    public InputExpression(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }
}
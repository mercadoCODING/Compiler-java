package AST;

import gui.CompilerGUI;
import modules.Expression;

import javax.swing.*;
import java.util.Map;
import java.util.Scanner;

public class InputExpression implements Expression {
    private final String prompt;

    public InputExpression(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        //Scanner scanner = new Scanner(System.in);
        //System.out.print(prompt + " ");
        return JOptionPane.showInputDialog(null, prompt);
        //return scanner.nextLine();
    }
}
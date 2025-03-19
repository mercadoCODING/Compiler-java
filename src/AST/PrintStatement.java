package AST;

import modules.Expression;
import modules.Statement;

import java.util.Map;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object result = expression.evaluate(symbolTable);

        if (result instanceof String && ((String) result).startsWith("INPUT(")) {
            String variableName = ((String) result).substring(6, ((String) result).length() - 1).trim();
            InputStatement inputStatement = new InputStatement(variableName);
            inputStatement.execute(symbolTable);
            result = symbolTable.get(variableName);
        }

        System.out.println(result);
    }
}

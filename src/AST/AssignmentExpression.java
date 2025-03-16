package AST;

import modules.Expression;
import modules.Statement;
import java.util.Map;

public class AssignmentExpression implements Expression {

    private final String variableName;
    private final Expression value;

    public AssignmentStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        System.out.println(expression);
        if (!symbolTable.containsKey(variableName)) {
            throw new RuntimeException("Variable '" + variableName + "' is not declared.");
        }

        Object value = expression.evaluate(symbolTable);
        Object existingValue = symbolTable.get(variableName);

        if (existingValue instanceof Integer && value instanceof Integer) {
            symbolTable.put(variableName, value);
        } else if (existingValue instanceof String && value instanceof String) {
            symbolTable.put(variableName, value);
        } else {
            throw new RuntimeException("Type mismatch in assignment to variable '" + variableName + "'");
        }
    }
}

package AST;

import modules.Expression;

import java.util.Map;

public class AssignmentExpression implements Expression {

    private final String variableName;
    private final Expression value;

    public AssignmentExpression(String variableName, Expression value) {
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        Object evaluatedValue = value.evaluate(symbolTable);
        symbolTable.put(variableName, evaluatedValue);
        return evaluatedValue;
    }
}

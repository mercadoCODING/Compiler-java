package AST;

import modules.Expression;

import java.util.Map;

public class NumberLiteral implements Expression {
    private final double value;

    public NumberLiteral(double value){
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        return value;
    }
}

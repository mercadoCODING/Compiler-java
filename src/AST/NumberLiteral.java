package AST;

import modules.Expression;

import java.util.Map;

public class NumberLiteral implements Expression {
    private final int value;

    public NumberLiteral(int value){
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        /*if (value == Math.floor(value)) {
            return (int) value;
        }*/
        return value;
    }
}

package AST;

import modules.Expression;

import java.util.Map;

public class StringLiteral implements Expression {
    private final String value;

    public StringLiteral(String value){

        if(value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2){
            this.value = value.substring(1, value.length() - 1);
        }else {
            this.value = value;
        }
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        return value;
    }
}

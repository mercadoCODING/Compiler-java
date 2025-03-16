package AST;

import modules.Expression;

import java.util.Map;

public class VariableReference implements Expression {
    private final String varName;

    public VariableReference(String varName){
        this.varName = varName;
    }

    public String getName() {
        return varName;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        if(symbolTable.containsKey(varName)){
            return symbolTable.get(varName);
        }
        throw new RuntimeException("Undefined variable: " + varName);
    }
}

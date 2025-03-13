package AST;

import modules.Expression;
import modules.Statement;

import java.util.Map;

//handle declaration

public class DeclarationStatement implements Statement {
    private final String varName;
    private final Expression expression;

    public DeclarationStatement(String varName, Expression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object value = expression.evaluate(symbolTable);
        symbolTable.put(varName,value);
        System.out.println("Declared " + varName + " = " + value); //log
    }
}

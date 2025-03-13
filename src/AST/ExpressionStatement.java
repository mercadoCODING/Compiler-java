package AST;

import modules.Expression;
import modules.Statement;

import java.util.Map;

//handle expression and evaluates it
public class ExpressionStatement implements Statement {
    private final Expression expression;

    public ExpressionStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object result = expression.evaluate(symbolTable);
        System.out.println("Result: " + result);
    }
}

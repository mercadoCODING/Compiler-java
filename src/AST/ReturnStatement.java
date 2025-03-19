package AST;

import modules.Expression;
import modules.Statement;
import util.ReturnException;

import java.util.Map;

public class ReturnStatement implements Statement {
    private final Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object returnValue = expression.evaluate(symbolTable);
        throw new ReturnException(returnValue);
    }
}

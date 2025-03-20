package AST;

import modules.Statement;

import java.util.Map;

//wrapper class for FunctionCallExpression to statement
public class FunctionCallStatement implements Statement {
    private final FunctionCallExpression functionCallExpr;

    public FunctionCallStatement(FunctionCallExpression functionCallExpr) {
        this.functionCallExpr = functionCallExpr;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        functionCallExpr.evaluate(symbolTable);
    }
}

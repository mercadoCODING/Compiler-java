package AST;

import modules.Expression;

import java.util.Map;

public class ArtihmeticExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    public ArtihmeticExpression(Expression left, Expression right, String operator){
        this.left = left;
        this.right = right;
        this.operator = operator;
    }



    @Override
    public Object evaluate(Map<String, Object> symbolTable) {

        Object leftVal = left.evaluate(symbolTable);
        Object rightVal = right.evaluate(symbolTable);

        if(operator.equals("+")){

            if(leftVal instanceof Number && rightVal instanceof Number){
                return ((Number) leftVal).intValue() + ((Number) rightVal).intValue();
            } else {
                return leftVal.toString() + rightVal.toString();
            }
        }
        if(leftVal instanceof Integer && rightVal instanceof Integer){
            int l = (Integer) leftVal;
            int r = (Integer) rightVal;
            switch (operator){
                case "-":
                    return l - r;
                case "*":
                    return l * r;
                case "/":
                    if(r == 0){
                        throw new RuntimeException("Cannot divide by zero");
                    }
                    return  l/r;
            }
        }

        throw new RuntimeException("Unsupported operand types for operator " + operator + ": " +
                leftVal.getClass() + " and " + rightVal.getClass());
    }
}

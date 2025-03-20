package AST;

import modules.Expression;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionCallExpression implements Expression {
    private final String functionName;
    private final List<Expression> arguments;

    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Object evaluate(Map<String, Object> symbolTable) {
        Object functionObject = symbolTable.get(functionName);
        if (!(functionObject instanceof FunctionDeclaration function)) {
            throw new RuntimeException("Undefined function: " + functionName);
        }

        // Evaluate all the arguments.
        List<Object> evaluatedArgs = arguments.stream()
                .map(arg -> arg.evaluate(symbolTable))
                .collect(Collectors.toList());

        // Return the result of the function invocation.
        return function.invoke(symbolTable, evaluatedArgs);
    }


}

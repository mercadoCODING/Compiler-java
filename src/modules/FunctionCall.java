package modules;

import AST.FunctionDeclaration;
import util.ReturnException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionCall implements Statement{
    private final String functionName;
    private final List<Expression> arguments;

    public FunctionCall(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object functionObject = symbolTable.get(functionName);
        if (!(functionObject instanceof FunctionDeclaration function)) {
            throw new RuntimeException("Undefined function: " + functionName);
        }

        List<Object> evaluatedArgs = arguments.stream()
                .map(arg -> arg.evaluate(symbolTable))
                .toList();

        function.invoke(symbolTable, evaluatedArgs);
    }
}


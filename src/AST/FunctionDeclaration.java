package AST;

import modules.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ReturnException;


public class FunctionDeclaration implements Statement {

    private final String functionName;
    private final List<String> parameters;
    private final List<Statement> body;

    public FunctionDeclaration(String functionName, List<String> parameters, List<Statement> body) {
        this.functionName = functionName;
        this.parameters = parameters;
        this.body = body;
    }


    @Override
    public void execute(Map<String, Object> symbolTable) {
        symbolTable.put(functionName,this);
    }

    public Object invoke(Map<String, Object> symbolTable, List<Object> args) {
        if (args.size() != parameters.size()) {
            throw new RuntimeException("Argument count mismatch for function " + functionName);
        }

        Map<String, Object> localScope = new HashMap<>(symbolTable);

        for (int i = 0; i < parameters.size(); i++) {
            localScope.put(parameters.get(i), args.get(i));
        }

        try {
            for (Statement statement : body) {
                statement.execute(localScope);
            }
        } catch (ReturnException returnException) {
            return returnException.getReturnValue();
        }


        return null;
    }
}

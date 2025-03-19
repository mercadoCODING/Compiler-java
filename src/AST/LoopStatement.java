package AST;

import modules.Statement;
import modules.Expression;
import java.util.List;
import java.util.Map;

public class LoopStatement implements Statement {
    private final Expression startCondition;
    private final Expression endCondition;
    private final List<Statement> body;

    public LoopStatement(Expression startCondition, Expression endCondition, List<Statement> body) {
        this.startCondition = startCondition;
        this.endCondition = endCondition;
        this.body = body;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object startValue = startCondition.evaluate(symbolTable);
        Object endValue = endCondition.evaluate(symbolTable);

        if (!(startValue instanceof Integer) || !(endValue instanceof Integer)) {
            throw new RuntimeException("LOOP conditions must be numbers");
        }

        int start = (Integer) startValue;
        int end = (Integer) endValue;


        if (!(startCondition instanceof VariableReference)) {
            throw new RuntimeException("Invalid loop variable reference in LOOP statement.");
        }
        String loopVariable = ((VariableReference) startCondition).getName();


        if (!symbolTable.containsKey(loopVariable)) {
            symbolTable.put(loopVariable, start);
        }


        if (start <= end) {

            while ((int) symbolTable.get(loopVariable) <= end) {
                if (executeBody(symbolTable)) return;
            }
        } else {
            // Decrementing loop: again, rely on loop body update.
            while ((int) symbolTable.get(loopVariable) >= end) {
                if (executeBody(symbolTable)) return;
            }
        }
    }


    private boolean executeBody(Map<String, Object> symbolTable) {
        for (Statement statement : body) {
            if (statement instanceof BreakStatement) {
                return true;
            }
            statement.execute(symbolTable);
        }
        return false;
    }


}

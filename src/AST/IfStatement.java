package AST;

import modules.Expression;
import modules.Statement;

import java.util.List;
import java.util.Map;

public class IfStatement implements Statement {

    private final Expression condition;
    private final List<Statement> thenBranch;
    private final List<ElseIfBlock> elseIfBranches;
    private final List<Statement> elseBranch;

    public IfStatement(Expression condition, List<Statement> thenBranch, List<ElseIfBlock> elseIfBranches,  List<Statement> elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseIfBranches = elseIfBranches;
        this.elseBranch = elseBranch;
    }

    @Override
    public void execute(Map<String, Object> symbolTable) {
        Object condValue = condition.evaluate(symbolTable);
        boolean conditionResult;

        if (condValue instanceof Boolean) {
            conditionResult = (Boolean) condValue;
        } else if (condValue instanceof Number) {

            conditionResult = ((Number) condValue).doubleValue() != 0;
        } else {
            throw new RuntimeException("Condition in IF statement must evaluate to a boolean or number.");
        }

        if (conditionResult) {
            for (Statement stmt : thenBranch) {
                stmt.execute(symbolTable);
            }
            return;
        }

        for (ElseIfBlock elseIf : elseIfBranches) {
            Object elseIfCondition = elseIf.getCondition().evaluate(symbolTable);
            boolean elseIfResult;
            if (elseIfCondition instanceof Boolean) {
                elseIfResult = (Boolean) elseIfCondition;
            } else if (elseIfCondition instanceof Number) {
                elseIfResult = ((Number) elseIfCondition).doubleValue() != 0;
            } else {
                throw new RuntimeException("Condition in ELSE IF statement must evaluate to a boolean or number.");
            }
            if (elseIfResult) {
                for (Statement stmt : elseIf.getBranch()) {
                    stmt.execute(symbolTable);
                }
                return;
            }
        }

        if (elseBranch != null) {
            for (Statement stmt : elseBranch) {
                stmt.execute(symbolTable);
            }
        }
    }

}

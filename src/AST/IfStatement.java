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

        if(!(condValue instanceof Boolean)){
            throw new RuntimeException("Condition in IF statement must evaluate to a boolean.");
        }

        if ((Boolean) condValue) {
            for (Statement stmt : thenBranch) {
                stmt.execute(symbolTable);
            }
            return;
        }

        for (ElseIfBlock elseIf : elseIfBranches) {
            Object elseIfCondition = elseIf.getCondition().evaluate(symbolTable);
            if (elseIfCondition instanceof Boolean && (Boolean) elseIfCondition) {
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

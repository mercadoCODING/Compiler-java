package AST;

import modules.Expression;
import modules.Statement;

import java.util.List;

public class ElseIfBlock {
    private final Expression condition;
    private final List<Statement> branch;

    public ElseIfBlock(Expression condition, List<Statement> branch) {
        this.condition = condition;
        this.branch = branch;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getBranch() {
        return branch;
    }


}

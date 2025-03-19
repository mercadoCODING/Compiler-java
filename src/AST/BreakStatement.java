package AST;

import modules.Statement;

import java.util.Map;

public class BreakStatement implements Statement {
    @Override
    public void execute(Map<String, Object> symbolTable) {
        // handled in LoopStatement class
    }
}

package modules;

import java.util.Map;

public interface Statement {
    void execute(Map<String,Object> symbolTable);
}

package modules;

import java.util.Map;

//symbol table
public interface Statement {
    void execute(Map<String,Object> symbolTable);
}

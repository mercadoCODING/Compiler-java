package modules;

import java.util.Map;

//symbol table

public interface Expression {
    Object evaluate(Map<String,Object> symbolTable);
}

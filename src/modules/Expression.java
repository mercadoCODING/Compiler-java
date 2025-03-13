package modules;

import java.util.Map;

public interface Expression {
    Object evaluate(Map<String,Object> symbolTable);
}

package compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import components.Lexer;
import components.Parser;
import components.Token;
import modules.Statement;


public class CodewareCompiler {

    //call sa main class para mag run
    public CodewareCompiler(String code){
        List<Token> tokens = getTokens(code);
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();


        Map<String, Object> symbolTable = new HashMap<>();


        for (Statement stmt : statements) {
            stmt.execute(symbolTable);
        }
    }

    //input taker

    //Completed for more verification paki test nalang QA
    private static List<Token> getTokens(String code) {
        Lexer lexer = new Lexer();
        return lexer.tokenize(code);
    }

}

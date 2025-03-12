package components;

public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void eat(Token.Type type) {
        if (currentToken.type == type) {
            currentToken = lexer.getNextToken();
        } else {
            throw new RuntimeException("Syntax error");
        }
    }

    // Evaluates NUMBER expressions
    public int exprAsNumber() {
        return evaluateNumberExpr();
    }

    // Evaluates WORD expressions
    public String exprAsWord() {
        return evaluateWordExpr();
    }

    // Arithmetic logic for NUMBERS
    private int evaluateNumberExpr() {
        int result = Integer.parseInt(term());
        while (currentToken.type == Token.Type.PLUS || currentToken.type == Token.Type.MINUS ) {
            if (currentToken.type == Token.Type.PLUS) {
                eat(Token.Type.PLUS);
                result += Integer.parseInt(term());
            } else {
                eat(Token.Type.MINUS);
                result -= Integer.parseInt(term());
            }
        }
        return result;
    }

    // String concatenation logic for WORDS
    private String evaluateWordExpr() {
        StringBuilder result = new StringBuilder(factor());
        while (currentToken.type == Token.Type.PLUS) {
            eat(Token.Type.PLUS);
            result.append(factor());
        }
        return result.toString();
    }


    private String term() {
        int result = Integer.parseInt(factor()); // Start with a NUMBER
        while (currentToken.type == Token.Type.MULTIPLY || currentToken.type == Token.Type.DIVIDE) {
            if (currentToken.type == Token.Type.MULTIPLY) {
                eat(Token.Type.MULTIPLY);
                result *= Integer.parseInt(factor());
            } else {
                eat(Token.Type.DIVIDE);
                int divisor = Integer.parseInt(factor());
                if (divisor == 0) {
                    throw new ArithmeticException("Division by zero error");
                }
                result /= divisor;
            }
        }
        return String.valueOf(result);
    }

    // Handles individual tokens (NUMBER or WORD)
    private String factor() {
        Token token = currentToken;
        if (token.type == Token.Type.NUMBER) {
            eat(Token.Type.NUMBER);
            return token.value;
        } else if (token.type == Token.Type.WORD) {
            eat(Token.Type.WORD);
            return token.value;
        }
        throw new RuntimeException("Invalid factor");
    }
}

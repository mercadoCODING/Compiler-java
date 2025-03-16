package components;

import AST.*;
import modules.Expression;
import modules.Statement;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Parse all tokens into a list of statements.
    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        while (current < tokens.size()) {
            statements.add(parseStatement());
        }
        return statements;
    }


    private Statement parseStatement() {

        if(current < tokens.size() && tokens.get(current).getType() == Token.TokenType.LOOP){
            consume(Token.TokenType.LOOP, "Expected 'LOOP' keyword");
            Expression startCondition = parseExpression();

            consume(Token.TokenType.TO, "Expected 'TO' in loop condition");
            Expression endCondition = parseExpression();


            consume(Token.TokenType.LEFT_BRACE, "Expected '{' before loop body");

            List<Statement> bodyStatements = new ArrayList<>();
            while (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_BRACE) {
                if (tokens.get(current).getType() == Token.TokenType.BREAK) {
                    consume(Token.TokenType.BREAK, "Expected 'BREAK' keyword");
                    bodyStatements.add(new BreakStatement());
                } else {
                    bodyStatements.add(parseStatement());
                }
            }
            consume(Token.TokenType.RIGHT_BRACE, "Expected '}' after loop body");
            return new LoopStatement(startCondition, endCondition, bodyStatements);
        }

        if (current < tokens.size() &&
                (tokens.get(current).getType() == Token.TokenType.NUMBER ||
                        tokens.get(current).getType() == Token.TokenType.WORD)) {

            Token typeToken = tokens.get(current++);
            Token varToken = consume(Token.TokenType.IDENTIFIER, "Expected variable name after type");

            Expression expr;
            if (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.ASSIGN) {
                consume(Token.TokenType.ASSIGN, "Expected '=' after variable name");
                expr = parseExpression();
            } else {
                // Default initializer based on type keyword.
                if (typeToken.getType() == Token.TokenType.NUMBER) {
                    expr = new NumberLiteral(0);
                } else if (typeToken.getType() == Token.TokenType.WORD) {
                    expr = new StringLiteral("\"\"");
                } else {
                    expr = new NumberLiteral(0);
                }
            }
            consume(Token.TokenType.SEMICOLON, "Expected ';' after declaration");
            return new DeclarationStatement(varToken.getValue(), expr);
        } else {
            Expression expr = parseAssignment();
            consume(Token.TokenType.SEMICOLON, "Expected ';' after expression");
            return new ExpressionStatement(expr);
        }
    }




    private Expression parseAssignment() {
        Expression expr = parseExpression();
        if (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.ASSIGN) {

            if (!(expr instanceof VariableReference)) {
                throw new RuntimeException("Invalid assignment target at token " +
                        (current < tokens.size() ? tokens.get(current).getValue() : "EOF"));
            }

            consume(Token.TokenType.ASSIGN, "Expected '=' after variable");

            Expression value = parseAssignment();

            return new AssignmentExpression(((VariableReference) expr).getName(), value);
        }
        return expr;
    }


    // Parse expressions for addition and subtraction.
    private Expression parseExpression() {
        Expression expr = parseTerm();
        while (current < tokens.size() &&
                tokens.get(current).getType() == Token.TokenType.OPERATOR &&
                (tokens.get(current).getValue().equals("+") || tokens.get(current).getValue().equals("-"))) {
            Token op = tokens.get(current++);
            Expression right = parseTerm();
            expr = new ArtihmeticExpression(expr,right, op.getValue());
        }
        return expr;
    }

    // Parse terms for multiplication and division.
    private Expression parseTerm() {
        Expression expr = parseFactor();
        while (current < tokens.size() &&
                tokens.get(current).getType() == Token.TokenType.OPERATOR &&
                (tokens.get(current).getValue().equals("*") || tokens.get(current).getValue().equals("/"))) {
            Token op = tokens.get(current++);
            Expression right = parseFactor();
            expr = new ArtihmeticExpression(expr, right, op.getValue());
        }
        return expr;
    }

    // Parse a factor: number literal, string literal, or identifier.
    private Expression parseFactor() {
        Token token = tokens.get(current);
        if (match(Token.TokenType.NUMBER_LITERAL)) {
            return new NumberLiteral(Integer.parseInt(token.getValue()));
        } else if (match(Token.TokenType.WORD_LITERAL)) {
            return new StringLiteral(token.getValue());
        } else if (match(Token.TokenType.IDENTIFIER)) {
            return new VariableReference(token.getValue());
        }
        throw new RuntimeException("Expected number, string, or identifier, found: " + token.getValue());
    }

    // Helper: if the current token matches the expected type, advance.
    private boolean match(Token.TokenType type) {
        if (current < tokens.size() && tokens.get(current).getType() == type) {
            current++;
            return true;
        }
        return false;
    }

    // Helper: ensure the current token is of the expected type.
    private Token consume(Token.TokenType type, String errorMessage) {
        if (current < tokens.size() && tokens.get(current).getType() == type) {
            return tokens.get(current++);
        }
        throw new RuntimeException(errorMessage + " at token " +
                (current < tokens.size() ? tokens.get(current).getValue() : "EOF"));
    }
}

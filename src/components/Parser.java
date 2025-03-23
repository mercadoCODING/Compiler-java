package components;

import AST.*;
import AST.ElseIfBlock;
import modules.Expression;
import modules.Statement;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    //core parser
    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        current = 0;
        while (current < tokens.size()) {
            statements.add(parseStatement());
        }
        return statements;
    }

    //helper method para easy process
    private boolean match(Token.TokenType type) {
        if (current < tokens.size() && tokens.get(current).getType() == type) {
            current++;
            return true;
        }
        return false;
    }

    private Token consume(Token.TokenType type, String errorMessage) {
        if (current < tokens.size() && tokens.get(current).getType() == type) {
            return tokens.get(current++);
        }
        throw new RuntimeException(errorMessage + " at token " +
                (current < tokens.size() ? tokens.get(current).getValue() : "EOF"));
    }

 //parser for statements
    private Statement parseStatement() {

        if (match(Token.TokenType.RETURN)) {
            Expression returnValue = null;


            if (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.SEMICOLON) {
                returnValue = parseExpression();
            }

            consume(Token.TokenType.SEMICOLON, "Expected ';' after RETURN statement");

            return new ReturnStatement(returnValue);
        }


        if(tokens.get(current).getType() == Token.TokenType.FUNC){
            //System.out.println("FUNC detected at position: " + current);
            return parseFunctionDeclaration();
        }

        if (current < tokens.size()
                && tokens.get(current).getType() == Token.TokenType.IDENTIFIER
                && current + 1 < tokens.size()
                && tokens.get(current + 1).getType() == Token.TokenType.LEFT_PAREN) {
            FunctionCallExpression callExpr = parseFunctionCallExpression();
            consume(Token.TokenType.SEMICOLON, "Expected ';' after function call");
            return new FunctionCallStatement(callExpr);
        }


        if (match(Token.TokenType.PRINT)) {
            Expression value;

            if (match(Token.TokenType.LEFT_PAREN)) {
                value = parseExpression();
                consume(Token.TokenType.RIGHT_PAREN, "Expected ')' after PRINT argument");
            } else {
                value = parseExpression();
            }

            consume(Token.TokenType.SEMICOLON, "Expected ';' after PRINT statement");
            return new PrintStatement(value);
        }

        if (match(Token.TokenType.INPUT)) {
            consume(Token.TokenType.LEFT_PAREN, "Expected '(' after 'INPUT'");
            Token variable = consume(Token.TokenType.IDENTIFIER, "Expected variable name after 'INPUT'");
            consume(Token.TokenType.RIGHT_PAREN, "Expected ')' after variable name");
            consume(Token.TokenType.SEMICOLON, "Expected ';' after INPUT statement");
            return new InputStatement(variable.getValue());
        }

        if (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.LOOP) {
            consume(Token.TokenType.LOOP, "Expected 'LOOP' keyword");

            Expression startCondition;
            if (match(Token.TokenType.NUMBER_LITERAL)) {
                startCondition = new NumberLiteral(Integer.parseInt(tokens.get(current - 1).getValue()));
            } else {
                startCondition = parseExpression();
            }

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



        if (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.IF) {
            consume(Token.TokenType.IF, "Expected 'IF' keyword");
            Expression condition = parseLogicalExpression();

            consume(Token.TokenType.LEFT_BRACE, "Expected '{' before IF body");

            List<Statement> ifBody = new ArrayList<>();
            while (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_BRACE) {
                ifBody.add(parseStatement());
            }
            consume(Token.TokenType.RIGHT_BRACE, "Expected '}' after IF body");

            List<ElseIfBlock> elseIfBlocks = new ArrayList<>();
            while (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.ELIF) {
                consume(Token.TokenType.ELIF, "Expected 'ELSE IF' keyword");
                Expression elseIfCondition = parseLogicalExpression();

                consume(Token.TokenType.LEFT_BRACE, "Expected '{' before ELSE IF body");

                List<Statement> elseIfBody = new ArrayList<>();
                while (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_BRACE) {
                    elseIfBody.add(parseStatement());
                }
                consume(Token.TokenType.RIGHT_BRACE, "Expected '}' after ELSE IF body");

                elseIfBlocks.add(new ElseIfBlock(elseIfCondition, elseIfBody));
            }


            List<Statement> elseBody = null;
            if (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.ELSE) {
                consume(Token.TokenType.ELSE, "Expected 'ELSE' keyword");
                consume(Token.TokenType.LEFT_BRACE, "Expected '{' before ELSE body");

                elseBody = new ArrayList<>();
                while (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_BRACE) {
                    elseBody.add(parseStatement());
                }
                consume(Token.TokenType.RIGHT_BRACE, "Expected '}' after ELSE body");
            }

            return new IfStatement(condition, ifBody, elseIfBlocks, elseBody);
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

    /*private InputExpression getInputExpression(){
        if (match(Token.TokenType.INPUT)) {
            consume(Token.TokenType.LEFT_PAREN, "Expected '(' after INPUT");


            Token promptToken = consume(Token.TokenType.WORD_LITERAL, "Expected string inside INPUT()");

            consume(Token.TokenType.RIGHT_PAREN, "Expected ')' after INPUT prompt");
            return new InputExpression(promptToken.getValue());
        }
        return null;
    }*/



    //Parse operators and delimiters and assignments
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

    private Expression parseLogicalExpression() {
        Expression expr = parseTerm();
        while (current < tokens.size() &&
                tokens.get(current).getType() == Token.TokenType.LOGICAL_OPERATOR &&
                (tokens.get(current).getValue().equals("==") ||
                        tokens.get(current).getValue().equals("!=") ||
                        tokens.get(current).getValue().equals("<")  ||
                        tokens.get(current).getValue().equals(">")  ||
                        tokens.get(current).getValue().equals("<=") ||
                        tokens.get(current).getValue().equals(">="))) {
            Token op = tokens.get(current++);
            Expression right = parseFactor();
            expr = new BinaryExpression(expr, right, op.getValue());
        }
        return expr;
    }


    // Parse Expressions for arithmetic
    private Expression parseExpression() {
        //getInputStatement();
        //getInputExpression();
        if (match(Token.TokenType.INPUT)) {
            consume(Token.TokenType.LEFT_PAREN, "Expected '(' after INPUT");


            Token promptToken = consume(Token.TokenType.WORD_LITERAL, "Expected string inside INPUT()");

            consume(Token.TokenType.RIGHT_PAREN, "Expected ')' after INPUT prompt");
            return new InputExpression(promptToken.getValue());
        }

        Expression expr = parseTerm();
        while (current < tokens.size() &&
                tokens.get(current).getType() == Token.TokenType.OPERATOR &&
                (tokens.get(current).getValue().equals("+") || tokens.get(current).getValue().equals("-"))) {
            Token op = tokens.get(current++);
            Expression right = parseTerm();
            expr = new BinaryExpression(expr,right, op.getValue());
        }
        return expr;
    }

    private Expression parseTerm() {
        Expression expr = parseFactor();
        while (current < tokens.size() &&
                tokens.get(current).getType() == Token.TokenType.OPERATOR &&
                (tokens.get(current).getValue().equals("*") || tokens.get(current).getValue().equals("/"))) {
            Token op = tokens.get(current++);
            Expression right = parseFactor();
            expr = new BinaryExpression(expr, right, op.getValue());
        }
        return expr;
    }

    // Parse Literals
    private Expression parseFactor() {
        Token token = tokens.get(current);

        if (match(Token.TokenType.NUMBER_LITERAL)) {
            return new NumberLiteral(Integer.parseInt(token.getValue()));
        } else if (match(Token.TokenType.WORD_LITERAL)) {
            return new StringLiteral(token.getValue());
        } else if (match(Token.TokenType.IDENTIFIER)) {
            String id = token.getValue();
            if (current < tokens.size() && tokens.get(current).getType() == Token.TokenType.LEFT_PAREN) {
                //handle nested fnction

                consume(Token.TokenType.LEFT_PAREN, "Expected '(' in function call");
                List<Expression> arguments = new ArrayList<>();
                if (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_PAREN) {
                    do {
                        arguments.add(parseExpression());
                    } while (match(Token.TokenType.COMMA));
                }
                consume(Token.TokenType.RIGHT_PAREN, "Expected ')' in function call");
                return new FunctionCallExpression(id, arguments);
            }

            return new VariableReference(id);
        }
        throw new RuntimeException("Expected number, string, or identifier, found: " + token.getValue());
    }


    //Parse Functions here
    private FunctionDeclaration parseFunctionDeclaration() {
        consume(Token.TokenType.FUNC, "Expected 'FUNC' keyword.");
        String functionName = consume(Token.TokenType.IDENTIFIER, "Expected function name.").getValue();
        consume(Token.TokenType.LEFT_PAREN, "Expected '(' after function name.");
        List<String> parameters = new ArrayList<>();


        if (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_PAREN) {
            do {
                if (tokens.get(current).getType() == Token.TokenType.NUMBER || tokens.get(current).getType() == Token.TokenType.WORD) {
                    consume(tokens.get(current).getType(), "Expected type token");
                }
                parameters.add(consume(Token.TokenType.IDENTIFIER, "Expected parameter name").getValue());
            } while (match(Token.TokenType.COMMA));
        }
        consume(Token.TokenType.RIGHT_PAREN, "Expected ')' after function parameters");

        consume(Token.TokenType.LEFT_BRACE, "Expected '{' before function body");
        List<Statement> body = new ArrayList<>();

        while (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_BRACE) {
            if (tokens.get(current).getType() == Token.TokenType.RETURN) {
                body.add(parseStatement());
                break;
            }else{
                body.add(parseStatement());
            }
        }

        consume(Token.TokenType.RIGHT_BRACE, "Expected '}' after function body");

        return new FunctionDeclaration(functionName, parameters, body);
    }

    private FunctionCallExpression parseFunctionCallExpression() {
        String functionName = consume(Token.TokenType.IDENTIFIER, "Expected function name").getValue();
        consume(Token.TokenType.LEFT_PAREN, "Expected '(' in function call");
        List<Expression> arguments = new ArrayList<>();
        if (current < tokens.size() && tokens.get(current).getType() != Token.TokenType.RIGHT_PAREN) {
            do {
                arguments.add(parseExpression());
            } while (match(Token.TokenType.COMMA));
        }
        consume(Token.TokenType.RIGHT_PAREN, "Expected ')' in function call");
        return new FunctionCallExpression(functionName, arguments);
    }



}
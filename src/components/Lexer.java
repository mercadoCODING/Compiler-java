package components;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//handle tokenization
public class Lexer {

    //symbol table
    private static final Map<String, Token.TokenType> keywords = new HashMap<>();

    static {
        keywords.put("NUMBER", Token.TokenType.NUMBER);
        keywords.put("WORD", Token.TokenType.WORD);
        keywords.put("LOOP", Token.TokenType.LOOP);
        keywords.put("IF", Token.TokenType.IF);
        keywords.put("ELSE", Token.TokenType.ELSE);
        keywords.put("TO", Token.TokenType.TO);
        keywords.put("BREAK", Token.TokenType.BREAK);
        keywords.put("ELIF", Token.TokenType.ELIF);
        keywords.put("FUNC", Token.TokenType.FUNC);
        keywords.put("RETURN", Token.TokenType.RETURN);
        keywords.put("PRINT", Token.TokenType.PRINT);
        keywords.put("INPUT", Token.TokenType.INPUT);
    }


    private static final Pattern tokenPatterns = Pattern.compile(
            "\\b(NUMBER|WORD|LOOP|IF|ELSE|TO|BREAK|ELIF|FUNC|RETURN|INPUT|PRINT)\\b|" + // Keywords
                    "[a-zA-Z_][a-zA-Z0-9_]*|" +     // Identifiers
                    "\\d+(\\.\\d+)?|" +             // Numbers
                    "\"[^\"]*\"|" +                 // Strings (Enhanced for INPUT prompt)
                    "(==|!=|<=|>=|[=+\\-*/;{}<>(),])" // Operators & Delimiters
    );


    public List<Token> tokenize(String code) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = tokenPatterns.matcher(code);

        while (matcher.find()) {
            String match = matcher.group();

            if (keywords.containsKey(match)) {
                tokens.add(new Token(keywords.get(match), match));
            } else if (match.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                tokens.add(new Token(Token.TokenType.IDENTIFIER, match));
            } else if (match.matches("\\d+(\\.\\d+)?")) {
                tokens.add(new Token(Token.TokenType.NUMBER_LITERAL, match));
            } else if (match.matches("\"[^\"]*\"")) {
                tokens.add(new Token(Token.TokenType.WORD_LITERAL, match));
            } else if (match.equals("{")) {
                tokens.add(new Token(Token.TokenType.LEFT_BRACE, match));
            } else if (match.equals("}")) {
                tokens.add(new Token(Token.TokenType.RIGHT_BRACE, match));
            } else if (match.equals("(")) {
                tokens.add(new Token(Token.TokenType.LEFT_PAREN, match));
            } else if (match.equals(")")) {
                tokens.add(new Token(Token.TokenType.RIGHT_PAREN, match));
            }else if (match.matches("(==|!=|<=|>=|[<>])")) {
                tokens.add(new Token(Token.TokenType.LOGICAL_OPERATOR, match));
            } else if (match.equals("=")) {
                tokens.add(new Token(Token.TokenType.ASSIGN, match));
            } else if (match.equals(";")) {
                tokens.add(new Token(Token.TokenType.SEMICOLON, match));
            } else if (match.equals(",")) {
                tokens.add(new Token(Token.TokenType.COMMA, match));
            } else if (match.matches("[+\\-*/]")) {
                tokens.add(new Token(Token.TokenType.OPERATOR, match));
            } else {
                tokens.add(new Token(Token.TokenType.UNKNOWN, match));
            }
        }

        return tokens;
    }




}




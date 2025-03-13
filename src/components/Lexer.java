package components;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//handle tokenization
public class Lexer {

    //symbol table
    private static final Map<String, Token.TokenType> keywords = Map.of(
            "NUMBER", Token.TokenType.NUMBER,
            "WORD", Token.TokenType.WORD,
            "LOOP", Token.TokenType.LOOP,
            "IF", Token.TokenType.IF,
            "ELSE", Token.TokenType.ELSE
    );


    private static final Pattern tokenPatterns = Pattern.compile(
            "\\b(NUMBER|WORD|LOOP|IF|ELSE)\\b|" +       // Keywords
                    "[a-zA-Z_][a-zA-Z0-9_]*|" +         // Identifiers
                    "\\d+(\\.\\d+)?|" +                 // Numbers
                    "\"[^\"]*\"|" +                     // Strings
                    "[=+\\-*/;]"                        // Operators & Delimiters
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
            } else if (match.equals("=")) {
                tokens.add(new Token(Token.TokenType.ASSIGN, match));
            } else if (match.equals(";")) {
                tokens.add(new Token(Token.TokenType.SEMICOLON, match));
            } else if (match.matches("[+\\-*/]")) {
                tokens.add(new Token(Token.TokenType.OPERATOR, match));
            } else {
                tokens.add(new Token(Token.TokenType.UNKNOWN, match));
            }
        }

        return tokens;
    }




}




package components;

public class Token {
    public enum TokenType {
        NUMBER, WORD, LOOP, IF, ELSE,
        IDENTIFIER, NUMBER_LITERAL, WORD_LITERAL,
        OPERATOR, DELIMITER, UNKNOWN , ASSIGN,SEMICOLON,
    }
    TokenType type;
    String value;
    Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue(){
        return value;
    }

}

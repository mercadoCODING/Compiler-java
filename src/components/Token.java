package components;

public class Token {
    public enum TokenType {
        NUMBER, WORD, LOOP, IF, ELSE,
        IDENTIFIER, NUMBER_LITERAL, WORD_LITERAL,
        OPERATOR, UNKNOWN , ASSIGN,SEMICOLON,RIGHT_BRACE,LEFT_BRACE,LEFT_PAREN,RIGHT_PAREN,COMMA,TO,BREAK,ELIF,LOGICAL_OPERATOR,FUNC,INPUT,PRINT,RETURN
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

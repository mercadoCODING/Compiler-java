package components;

public class Token {
    enum Type {NUMBER,WORD,PLUS,MINUS,MULTIPLY,DIVIDE,EOF}
    Type type;
    String value;

    Token(Type type, String value){
        this.type = type;
        this.value = value;
    }
}

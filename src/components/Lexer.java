package components;


public class Lexer {

    private final String input;
    private int pos;

    public Lexer(String input) {
        this.input = input.replaceAll("\s+", "");
    }

    Token getNextToken() {
        if (pos >= input.length()) return new Token(Token.Type.EOF, "");

        char current = input.charAt(pos);
        if (Character.isDigit(current)) {
            StringBuilder number = new StringBuilder();
            while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                number.append(input.charAt(pos++));
            }
            return new Token(Token.Type.NUMBER, number.toString());
        } else if (current == '"') {
            pos++; // Skip opening quote
            StringBuilder str = new StringBuilder();
            while (pos < input.length() && input.charAt(pos) != '"') {
                str.append(input.charAt(pos++));
            }
            pos++; // Skip closing quote
            return new Token(Token.Type.WORD, str.toString());
        }

        pos++;
        return switch (current) {
            case '+' -> new Token(Token.Type.PLUS, "+");
            case '-' -> new Token(Token.Type.MINUS, "-");
            case '*' -> new Token(Token.Type.MULTIPLY, "*");
            case '/' -> new Token(Token.Type.DIVIDE, "/");
            default -> throw new RuntimeException("Unknown character: " + current);
        };
    }
}




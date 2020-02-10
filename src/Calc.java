import java.util.ArrayList;
import java.util.List;

public class Calc {

    public List<Lexeme> lexemeAnalyze(String stringExpression) {

        int position = 0;
        List<Lexeme> lexemes = new ArrayList<Lexeme>();
        while (position < stringExpression.length()) {
            char character = stringExpression.charAt(position);
            switch (character) {
                case '(':
                    lexemes.add(new Lexeme(character, LexemesType.LEFT_BRACKET));
                    position++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(character, LexemesType.RIGHT_BRACKET));
                    position++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(character, LexemesType.OP_PLUS));
                    position++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(character, LexemesType.OP_MINUS));
                    position++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(character, LexemesType.OP_MUL));
                    position++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(character, LexemesType.OP_DIV));
                    position++;
                    continue;
                default:
                    if (character <= '9' && character >= '0') {
                        StringBuilder expression = new StringBuilder();
                        do {
                            expression.append(character);
                            position++;
                            if (position >= stringExpression.length()) {
                                break;
                            }
                            character = stringExpression.charAt(position);
                        } while (character <= '9' && character >= '0');
                        lexemes.add(new Lexeme(expression.toString(), LexemesType.NUMBER));
                    } else {
                        if (character != ' ') {
                            throw new RuntimeException("Unexpected character: " + character);
                        }
                        position++;
                    }
            }
        }
        lexemes.add(new Lexeme("", LexemesType.EOF));
        return lexemes;
    }

    /**
     * Refers to expr rule. Starts calculation
     *
     * @param lexemes
     * @return
     */
    public int calculate(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.getType() == LexemesType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusMinus(lexemes);
        }
    }

    /**
     * Refers to plusminus rule. Handles factors to execute subtracting or summation
     *
     * @param lexemes
     * @return
     */
    public int plusMinus(LexemeBuffer lexemes) {
        int value = multDiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case OP_PLUS:
                    value += multDiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multDiv(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    /**
     * Refers to multdiv rule. Handles factors to execute division or multiplication.
     *
     * @param lexemes
     * @return
     */
    public int multDiv(LexemeBuffer lexemes) {
        int value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIV:
                    value /= factor(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    /**
     * Refers to factor rule. Base operation, allocating numbers & parsing them to int.
     *
     * @param lexemes
     * @return
     */
    public int factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.getType()) {
            case NUMBER:
                return Integer.parseInt(lexeme.getValue());
            case LEFT_BRACKET:
                int value = plusMinus(lexemes);
                lexeme = lexemes.next();
                if (lexeme.getType() != LexemesType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.getType()
                            + " at position: " + lexemes.getPos());
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.getType()
                        + " at position: " + lexemes.getPos());
        }
    }

}

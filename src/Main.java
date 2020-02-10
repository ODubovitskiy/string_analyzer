import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Calc calc = new Calc();

        String expression = new Scanner(System.in).nextLine();

        List<Lexeme> lexemes = calc.lexemeAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        System.out.println(expression + " = " + calc.calculate(lexemeBuffer));

        /*
         * expr : plusminus* EOF ;
         * plusminus: multdiv ( ( '+' | '-' ) multdiv )* ;
         * multdiv : factor ( ( '*' | '/' ) factor )* ;
         * factor : NUMBER | '(' expr ')' ;
         */

    }
}
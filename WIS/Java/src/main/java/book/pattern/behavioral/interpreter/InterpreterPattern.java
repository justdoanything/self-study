package book.pattern.behavioral.interpreter;

public class InterpreterPattern {
    public static void main(String[] args) {
        Logic.Values.assign("A", true);
        Logic.Values.assign("B", false);

        Logic and = new AND(new Variable("A"), new Variable("B"));
        System.out.println(and.evaluate());

        Logic or = new OR(new Variable("A"), new Variable("B"));
        System.out.println(or.evaluate());

        Logic not = new AND(new Variable("A"), new NOT(new Variable("B")));
        System.out.println(not.evaluate());
    }
}

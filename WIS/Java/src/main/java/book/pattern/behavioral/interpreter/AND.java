package book.pattern.behavioral.interpreter;

public class AND implements Logic {
    Logic left, right;

    public AND(Logic left, Logic right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }
}

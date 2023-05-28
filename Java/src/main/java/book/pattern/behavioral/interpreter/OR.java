package book.pattern.behavioral.interpreter;

public class OR implements Logic {
    Logic left, right;

    public OR(Logic left, Logic right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }
}


package book.pattern.behavioral.interpreter;

public class NOT implements Logic {
    Logic value;

    public NOT(Logic value) {
        this.value = value;
    }

    @Override
    public boolean evaluate() {
        return !value.evaluate();
    }
}
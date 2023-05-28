package book.pattern.behavioral.interpreter;

public class Variable implements Logic {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean evaluate() {
        return Logic.Values.lookup(name);
    }
}

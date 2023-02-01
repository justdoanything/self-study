package book.pattern.behavioral.memento;

public class Memento {
    private final String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        return state;
    }
}

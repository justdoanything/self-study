package book.pattern.behavioral.memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private final List<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento memento) {
        this.mementos.add(memento);
    }

    public Memento getMemento(int index) {
        return mementos.get(index);
    }

    public int getMementoSize() {
        return mementos.size();
    }
}

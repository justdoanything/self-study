package book.pattern.behavioral.observer;

public interface Subject {
    void addSubscriber(Subscriber subscriber);

    void removeSubscriber(Subscriber subscriber);

    void notifyObserver();
}

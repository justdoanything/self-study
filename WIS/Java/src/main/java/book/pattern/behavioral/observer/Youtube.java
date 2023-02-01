package book.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class Youtube implements Subject {
    private List<Subscriber> subscribers = new ArrayList<>();
    private Post post;

    @Override
    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifyObserver() {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(post);
        }
    }

    public void uploadPost(Post post) {
        this.post = post;
    }
}

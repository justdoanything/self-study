package book.pattern.behavioral.observer;

public class ObserverPattern {
    public static void main(String[] args) {
        Subscriber subscriber1 = new Subscriber();
        Subscriber subscriber2 = new Subscriber();
        Subscriber subscriber3 = new Subscriber();

        Youtube youtube = new Youtube();
        youtube.addSubscriber(subscriber1);
        youtube.addSubscriber(subscriber2);
        youtube.addSubscriber(subscriber3);

        youtube.uploadPost(new Post("올해 바뀐 제도들에 대한 영상"));
        youtube.notifyObserver();
    }
}

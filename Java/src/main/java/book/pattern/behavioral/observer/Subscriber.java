package book.pattern.behavioral.observer;

public class Subscriber implements Observer {
    public void update(Post post){
        System.out.println("notice : " + post.getTitle() + " is updated.");
    }
}

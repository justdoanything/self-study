package book.effective;

// public static final 방식
public class Singleton {
    public static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static void main(String[] args) {

        System.out.println("test");
    }
}

// static factory method 방식
class Singleton2 {
    private static final Singleton2 INSTANCE = new Singleton2();

    private Singleton2() {}

    public static Singleton2 getInstance() {
        return INSTANCE;
    }
}

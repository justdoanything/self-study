package book.pattern.creational.singleton.a_eager;

public class SingletonClass {
    private static final SingletonClass instance = new SingletonClass();

    private SingletonClass(){}

    public static SingletonClass getInstance() {
        return instance;
    }
}

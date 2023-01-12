package book.pattern.creational.singleton.d_threadSafe;

public class SingletonClass {
    private static SingletonClass instance;

    private SingletonClass(){}

    public static synchronized SingletonClass getInstance() {
        if(instance == null) {
            instance = new SingletonClass();
        }
        return instance;
    }
}

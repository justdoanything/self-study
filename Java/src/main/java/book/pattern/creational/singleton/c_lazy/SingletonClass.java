package book.pattern.creational.singleton.c_lazy;

public class SingletonClass {
    private static SingletonClass instance;

    private SingletonClass(){}

    public static SingletonClass getInstance() {
        if(instance == null) {
            instance = new SingletonClass();
        }
        return instance;
    }
}

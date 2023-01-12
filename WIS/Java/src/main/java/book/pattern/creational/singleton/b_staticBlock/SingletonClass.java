package book.pattern.creational.singleton.b_staticBlock;

public class SingletonClass {
    private static SingletonClass instance;

    private SingletonClass(){}

    static {
        try {
            instance = new SingletonClass();
        }catch(Exception e){
            throw new RuntimeException("There is an exception in creating singleton class");
        }
    }

    public static SingletonClass getInstance() {
        return instance;
    }
}

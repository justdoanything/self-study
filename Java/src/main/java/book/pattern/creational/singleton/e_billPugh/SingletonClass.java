package book.pattern.creational.singleton.e_billPugh;

public class SingletonClass {
    private SingletonClass(){}

    private static class SingletonClassHelper {
        private static final SingletonClass INSTANCE = new SingletonClass();
    }

    public static SingletonClass getInstance()  {
        return SingletonClassHelper.INSTANCE;
    }
}

package book.pattern.creational.singleton.d_threadSafe;

public class SingletonClassDoubleCheck {
    private static SingletonClassDoubleCheck instance;

    private SingletonClassDoubleCheck(){}

    /* double checked locking */
    public static SingletonClassDoubleCheck getInstance() {
        if(instance == null) {
            synchronized (SingletonClassDoubleCheck.class) {
                if(instance == null) {
                    instance = new SingletonClassDoubleCheck();
                }
            }
        }
        return instance;
    }
}

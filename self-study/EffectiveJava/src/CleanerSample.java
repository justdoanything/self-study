import java.lang.ref.Cleaner;


/**
 * State 인스턴스가 Room 인스턴스를 참조하면 순환참조가 일어나서 청소되지 않는다.
 * try-resources문을 사용해서 Room 객체를 사용하면 프로그램이 정상적으로 청소됨("====방 청소!")을 볼 수 있지만
 * 단순하게 new Room(10);으로 선언해서 사용한다면 언제 청소될지 알 수 없다.
 */
public class CleanerSample implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();

    // 청소가 필요한 자원. 절대 CleanerSample을 참조하면 안된다.
    private static class State implements Runnable {
        int numJunkPiles; // 클래스 안에 쓰레기 수

        State(int numJunkPiles){
            this.numJunkPiles = numJunkPiles;
        }

        // close 메서드나 cleaner가 호출한다.
        @Override
        public void run() {
            System.out.println("====방 청소!");
            numJunkPiles = 0;
        }
    }

    // 클래스의 상태. cleanable과 공유한다.
    private final State state;

    // cleanable 객체. 수거 대상이 되면 클래스를 청소한다.
    private final Cleaner.Cleanable cleanable;

    public CleanerSample(int numJunkPiles){
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close(){
        cleanable.clean();
    }
}

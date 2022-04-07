import java.lang.ref.Cleaner;

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

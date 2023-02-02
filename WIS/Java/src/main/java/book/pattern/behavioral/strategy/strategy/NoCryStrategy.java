package book.pattern.behavioral.strategy.strategy;

public class NoCryStrategy implements SoundStrategy {
    @Override
    public void crying() {
        System.out.println("울지 않음");
    }
}

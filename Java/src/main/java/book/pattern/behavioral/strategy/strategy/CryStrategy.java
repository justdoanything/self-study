package book.pattern.behavioral.strategy.strategy;

public class CryStrategy implements SoundStrategy {
    @Override
    public void crying() {
        System.out.println("오리는 꽥꽥~!");
    }
}

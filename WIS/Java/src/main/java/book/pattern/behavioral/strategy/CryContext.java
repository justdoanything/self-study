package book.pattern.behavioral.strategy;

import book.pattern.behavioral.strategy.strategy.SoundStrategy;

public class CryContext {
    private SoundStrategy soundStrategy;

    public void crying() {
        soundStrategy.crying();
    }

    public void setSoundStrategy(SoundStrategy soundStrategy) {
        this.soundStrategy = soundStrategy;
    }
}

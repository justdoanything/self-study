package book.pattern.behavioral.strategy;

import book.pattern.behavioral.strategy.strategy.CryStrategy;
import book.pattern.behavioral.strategy.strategy.NoCryStrategy;

public class StrategyPattern {
    public static void main(String[] args) {
        CryContext person = new Person();
        CryContext duck = new Duck();

        person.setSoundStrategy(new NoCryStrategy());
        duck.setSoundStrategy(new CryStrategy());

        person.crying();;
        duck.crying();

        person.setSoundStrategy(new CryStrategy());
        person.crying();
        duck.crying();
    }
}

package book.pattern.behavioral.memento;

public class MementoPattern {
    public static void main(String[] args) {
        CareTaker careTaker = new CareTaker();
        Originator originator = new Originator();

        originator.setState("State #1");
        originator.setState("State #2");
        careTaker.addMemento(originator.saveStateToMemento());
        originator.setState("State #3");
        careTaker.addMemento(originator.saveStateToMemento());

        originator.setState("State #4");
        originator.setState("State #5");
        careTaker.addMemento(originator.saveStateToMemento());
        originator.setState("State #6");
        originator.setState("State #7");


        System.out.println("Current State : " + originator.getState());
        System.out.println("State Saved Count : " + careTaker.getMementoSize());
        System.out.println("State Saved First : " + careTaker.getMemento(0));
        System.out.println("State Saved Last : " + careTaker.getMemento(careTaker.getMementoSize()-1));
    }
}

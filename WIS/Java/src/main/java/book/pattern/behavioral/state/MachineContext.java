package book.pattern.behavioral.state;

public class MachineContext {
    State state;

    public MachineContext() {
        state = new NoCoinState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getState() {
        return state.getState();
    }

    public void insertCoin() {
        this.state.insertCoin(this);
    }

    public void returnCoin() {
        this.state.returnCoin(this);
    }
}

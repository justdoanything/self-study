package book.pattern.behavioral.state;

public interface State {
    void insertCoin(MachineContext machineContext);
    void returnCoin(MachineContext machineContext);
    int getCoinCount();
    String getState();
}

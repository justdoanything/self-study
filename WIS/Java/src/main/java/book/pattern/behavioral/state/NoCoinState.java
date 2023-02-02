package book.pattern.behavioral.state;

public class NoCoinState implements State {
    @Override
    public void insertCoin(MachineContext machineContext) {
        machineContext.setState(new CoinState());
        System.out.println("코인이 삽입되었습니다. [코인 : 1]");
    }

    @Override
    public void returnCoin(MachineContext machineContext) {
        System.out.println("코인이 없습니다.");
    }

    @Override
    public int getCoinCount() {
        return 0;
    }

    @Override
    public String getState() {
        return "코인이 없는 상태입니다.";
    }
}

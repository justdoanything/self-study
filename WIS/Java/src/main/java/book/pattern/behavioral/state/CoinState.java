package book.pattern.behavioral.state;

public class CoinState implements State {
    private int coinCount = 1;

    @Override
    public void insertCoin(MachineContext machineContext) {
        coinCount++;
        machineContext.setState(this);
        System.out.println("코인이 삽입되었습니다. [코인 : " + coinCount + "]");
    }

    @Override
    public void returnCoin(MachineContext machineContext) {
        if(--coinCount == 0){
            machineContext.setState(new NoCoinState());
        }
        System.out.println("코인이 반환되었습니다. [남은 코인 : " + coinCount + "]");
    }

    @Override
    public String getState() {
        return "코인이 있는 상태입니다. [남은 코인 : " + coinCount + "]";
    }

    @Override
    public int getCoinCount() {
        return coinCount;
    }
}

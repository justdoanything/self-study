package book.pattern.behavioral.state;

public class StatePattern {
    public static void main(String[] args) {
        MachineContext machineContext = new MachineContext();
        System.out.println(machineContext.getState());
        System.out.println("==============================");

        machineContext.insertCoin();
        machineContext.insertCoin();
        machineContext.insertCoin();
        machineContext.insertCoin();
        System.out.println(machineContext.getState());
        System.out.println("==============================");

        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        System.out.println(machineContext.getState());
        System.out.println("==============================");

        machineContext.insertCoin();
        System.out.println(machineContext.getState());
        System.out.println("==============================");
    }
}

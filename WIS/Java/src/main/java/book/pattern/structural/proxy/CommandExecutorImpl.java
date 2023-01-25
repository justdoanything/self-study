package book.pattern.structural.proxy;

public class CommandExecutorImpl implements CommandExecutor {
    @Override
    public void runCommand(String command) throws Exception {
        System.out.println("Execute Command : " + command);
        Runtime.getRuntime().exec(command);
    }
}

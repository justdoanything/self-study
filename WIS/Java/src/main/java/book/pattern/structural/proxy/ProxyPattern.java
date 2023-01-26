package book.pattern.structural.proxy;

public class ProxyPattern {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutorProxy("Admin", "WrongPassword");
        try{
            commandExecutor.runCommand("ls -ltr");
            commandExecutor.runCommand("rm -rf test.test");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
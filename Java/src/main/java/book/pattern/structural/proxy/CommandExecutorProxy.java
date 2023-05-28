package book.pattern.structural.proxy;

public class CommandExecutorProxy implements CommandExecutor {
    private boolean isAdmin;
    private CommandExecutor commandExecutor;

    public CommandExecutorProxy(String user, String pwd) {
        if("Admin".equals(user) && "Password".equals(pwd)){
            isAdmin = true;
        }
        commandExecutor = new CommandExecutorImpl();
    }

    @Override
    public void runCommand(String command) throws Exception {
        if(isAdmin) {
            commandExecutor.runCommand(command);
        }else {
            if(command.trim().startsWith("rm")){
                throw new RuntimeException("This command cannot run if not admin.");
            }else{
                commandExecutor.runCommand(command);
            }
        }
    }
}

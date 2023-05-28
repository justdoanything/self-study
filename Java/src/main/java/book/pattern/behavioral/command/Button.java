package book.pattern.behavioral.command;

public class Button {
    private Command command;
    public Button(Command command){
        this.command = command;
    }

    public void setCommand(Command command){
        this.command = command;
    }

    public void pressed() {
        command.execute();
    }
}

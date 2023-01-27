package book.pattern.behavioral.command.alarm;

import book.pattern.behavioral.command.Command;

public class AlarmStartCommand implements Command {
    private Alarm alarm;

    public AlarmStartCommand(Alarm alarm){
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.start();
    }
}

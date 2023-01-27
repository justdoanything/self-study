package book.pattern.behavioral.command;

import book.pattern.behavioral.command.alarm.Alarm;
import book.pattern.behavioral.command.alarm.AlarmStartCommand;
import book.pattern.behavioral.command.lamp.Lamp;
import book.pattern.behavioral.command.lamp.LampOnCommand;

public class CommandPattern {
    public static void main(String[] args) {
        Command lampOnCommand = new LampOnCommand(new Lamp());
        Command alarmStartCommand = new AlarmStartCommand(new Alarm());

        Button button = new Button(lampOnCommand);
        button.pressed();
        button.pressed();

        button.setCommand(alarmStartCommand);
        button.pressed();
        button.pressed();

        button.setCommand(lampOnCommand);
        button.pressed();
    }
}

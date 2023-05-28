package book.pattern.structural.bridge.model.employee;

import book.pattern.structural.bridge.model.team.Team;

public class EmployeeDev extends Employee {
    public EmployeeDev(Team team) {
        super(team);
    }

    @Override
    public void applyTeam() {
        System.out.print("This developer is in ");
        team.applyTeam();
    }
}

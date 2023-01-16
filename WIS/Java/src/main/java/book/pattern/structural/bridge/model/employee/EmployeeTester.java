package book.pattern.structural.bridge.model.employee;

import book.pattern.structural.bridge.model.team.Team;

public class EmployeeTester extends Employee {
    public EmployeeTester(Team team) {
        super(team);
    }

    @Override
    public void applyTeam() {
        System.out.print("This tester is in ");
        team.applyTeam();
    }
}

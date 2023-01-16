package book.pattern.structural.bridge.model.employee;

import book.pattern.structural.bridge.model.team.Team;

public abstract class Employee {
    protected Team team;

    public Employee(Team team) {
        this.team = team;
    }

    abstract public void applyTeam();
}

package book.pattern.structural.bridge;

import book.pattern.structural.bridge.model.employee.Employee;
import book.pattern.structural.bridge.model.employee.EmployeeDev;
import book.pattern.structural.bridge.model.employee.EmployeeTester;
import book.pattern.structural.bridge.model.team.TeamDev;
import book.pattern.structural.bridge.model.team.TeamTest;

public class BridgePattern {
    public static void main(String[] args) {
        Employee devEmployee = new EmployeeDev(new TeamDev());
        devEmployee.applyTeam();

        Employee testerEmployee = new EmployeeTester(new TeamTest());
        testerEmployee.applyTeam();
    }
}

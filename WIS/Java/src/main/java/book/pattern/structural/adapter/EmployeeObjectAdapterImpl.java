package book.pattern.structural.adapter;

import book.pattern.structural.adapter.model.Employee;
import book.pattern.structural.adapter.model.Team;

import java.util.List;

public class EmployeeObjectAdapterImpl implements EmployeeAdapter {
    private Team team = new Team();

    @Override
    public List<Employee> createDevEmployees(){
        return team.createDevTeam();
    }

    @Override
    public List<Employee> createTestEmployees(){
        List<Employee> emp = team.createDevTeam();
        return convertTeam(emp, "TEST");
    }

    @Override
    public List<Employee> createManagerEmployees(){
        List<Employee> emp = team.createDevTeam();
        return convertTeam(emp, "MANAGER");
    }

    private List<Employee> convertTeam(List<Employee> employees, String teamName) {
        employees.forEach(employee -> employee.setDepartment(teamName));
        return employees;
    }
}

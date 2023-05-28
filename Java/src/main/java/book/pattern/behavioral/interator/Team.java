package book.pattern.behavioral.interator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Team implements Aggregate {
    private List<Employee> employeeList = new ArrayList<>();
    private String teamName;
    private int maxCount;

    public Team(String teamName, int maxCount){
        this.teamName = teamName;
        this.maxCount = maxCount;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void addEmployee(Employee employee) {
        if(employeeList.size() >= maxCount){
            System.out.println("팀원을 더이상 추가할 수 없습니다.");
        }else {
            System.out.println(teamName + "팀에 " + employee.getName() + "을 추가했습니다.");
            employeeList.add(employee);
        }
    }

    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    @Override
    public Iterator getEmployeeIterator() {
        return new TeamIterator(this);
    }
}

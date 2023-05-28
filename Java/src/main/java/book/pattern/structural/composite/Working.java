package book.pattern.structural.composite;

import java.util.ArrayList;
import java.util.List;

/* Composite Object */
public class Working implements Employee {
    private List<Employee> employees = new ArrayList<>();

    @Override
    public void doWork(String work) {
        for(Employee employee: employees) {
            employee.doWork(work);
        }
    }

    public void add(Employee employee) {
        System.out.println("Add Employee : " + employee);
        this.employees.add(employee);
    }

    public void remove(Employee employee) {
        System.out.println("Remove Employee : " + employee);
        this.employees.remove(employee);
    }

    public void clear() {
        System.out.println("Remove All of Employee");
        this.employees.clear();
    }
}

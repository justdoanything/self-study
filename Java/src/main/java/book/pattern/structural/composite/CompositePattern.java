package book.pattern.structural.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositePattern {
    public static void main(String[] args) {
        Employee devEmployee = new DevEmployee("John");
        Employee testEmployee = new TestEmployee("Lin");

        Working working = new Working();
        working.add(devEmployee);
        working.add(testEmployee);

        working.doWork("Daily Scrum Meeting.");

        List<Employee> employees = new ArrayList<>();
        employees.add(working);
        employees.add(new DevEmployee("Beaver"));
        employees.add(new TestEmployee("Dan"));
        for(Employee employee: employees) {
            employee.doWork("Coffee Time");
        }
    }
}

package book.pattern.creational.factory;

import book.pattern.creational.factory.model.Developer;
import book.pattern.creational.factory.model.Employee;

public class DeveloperFactory implements EmployeeAbstractFactory {
    private String id;
    private String name;
    private String department;

    public DeveloperFactory(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    @Override
    public Employee createEmployee() {
        return new Developer(id, name, department);
    }
}

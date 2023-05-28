package book.pattern.creational.factory;

import book.pattern.creational.factory.model.Employee;

public class EmployeeFactory {
    public static Employee getEmployee(EmployeeAbstractFactory factory) {
        return factory.createEmployee();
    }
}

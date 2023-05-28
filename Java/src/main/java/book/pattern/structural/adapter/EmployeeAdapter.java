package book.pattern.structural.adapter;

import book.pattern.structural.adapter.model.Employee;

import java.util.List;

public interface EmployeeAdapter {
    public List<Employee> createDevEmployees();
    public List<Employee> createTestEmployees();
    public List<Employee> createManagerEmployees();
}

package book.pattern.creational.prototype.model;

import java.util.ArrayList;
import java.util.List;

public class Employee implements Cloneable {
    private List<String> employees;

    public Employee() {
        this.employees = new ArrayList<>();
    }

    public Employee(List<String> employees) {
        this.employees = employees;
    }

    public void loadData() {
        this.employees.add("John");
        this.employees.add("Pikan");
        this.employees.add("Pie");
        this.employees.add("Apple");
    }

    public List<String> getEmployees() {
        return this.employees;
    }

    public void addEmployee(String emplyee) {
        this.employees.add(emplyee);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<String> temp = new ArrayList<>();
        for(String employee : this.employees){
            temp.add(employee);
        }
        return new Employee(temp);
    }
}

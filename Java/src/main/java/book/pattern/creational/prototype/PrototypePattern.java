package book.pattern.creational.prototype;

import book.pattern.creational.prototype.model.Employee;

public class PrototypePattern {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee employee = new Employee();
        employee.loadData();

        Employee employee1 = (Employee) employee.clone();
        Employee employee2 = (Employee) employee.clone();

        employee1.addEmployee("Tier");
        employee2.addEmployee("Ruy");

        System.out.println(employee.getEmployees());
        System.out.println(employee1.getEmployees());
        System.out.println(employee2.getEmployees());

    }
}

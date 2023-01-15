package book.pattern.structural.adapter.model;

public class Employee {
    private String department;

    public Employee(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String toString() {
        return department;
    }
}

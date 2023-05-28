package book.pattern.creational.builder;

public class BuilderPattern {
    public static void main(String[] args) {
        Employee employee = new Employee.EmployeeBuilder(1, "1", "1").age(30).city("SEOUL").build();
        System.out.println(employee.toString());
    }

}

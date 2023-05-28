package book.pattern.structural.adapter;

public class AdapterPattern {
    public static void main(String[] args) {
        EmployeeAdapter employeeClassAdapter = new EmployeeClassAdapterImpl();
        employeeClassAdapter.createDevEmployees().forEach(System.out::println);
        employeeClassAdapter.createTestEmployees().forEach(System.out::println);
        employeeClassAdapter.createManagerEmployees().forEach(System.out::println);

        EmployeeAdapter employeeObjectAdapter = new EmployeeClassAdapterImpl();
        employeeObjectAdapter.createDevEmployees().forEach(System.out::println);
        employeeObjectAdapter.createTestEmployees().forEach(System.out::println);
        employeeObjectAdapter.createManagerEmployees().forEach(System.out::println);
    }
}

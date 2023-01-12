package book.pattern.creational.factory.model;

public class Developer extends Employee {
    private String id;
    private String name;
    private String department;

    public Developer(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    @Override
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDepartment() {
        return this.department;
    }
}

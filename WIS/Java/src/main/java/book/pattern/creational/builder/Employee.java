package book.pattern.creational.builder;

public class Employee {
    private int id;
    private String name;
    private String department;

    private int age;
    private String city;
    private boolean isLeader;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    public int getAge() { return age; }
    public String getCity() { return city; }
    public boolean isLeader() { return isLeader; }

    public String toString() {
        return "Employee = [" +
                    "id : " + id +
                    ", name : " + name +
                    ", department : " + department +
                    ", age : " + age +
                    ", city : " + city +
                    ", isLeader : " + isLeader + "]";
    }


    private Employee(EmployeeBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.department = builder.department;

        this.age = builder.age;
        this.city = builder.city;
        this.isLeader = builder.isLeader;
    }

    public static class EmployeeBuilder {
        private int id;
        private String name;
        private String department;

        private int age;
        private String city;
        private boolean isLeader;

        public EmployeeBuilder(int id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
        }

        public EmployeeBuilder age(int age){
            this.age = age;
            return this;
        }

        public EmployeeBuilder city(String city){
            this.city = city;
            return this;
        }

        public EmployeeBuilder isLeader(boolean isLeader){
            this.isLeader = isLeader;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}

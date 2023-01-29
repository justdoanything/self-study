package book.pattern.behavioral.interator;

public class Employee {
    private String teamName;
    private String name;

    public Employee(String teamName, String name){
        this.teamName = teamName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return teamName + "팀 소속 " + name;
    }
}

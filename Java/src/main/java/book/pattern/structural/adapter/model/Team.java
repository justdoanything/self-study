package book.pattern.structural.adapter.model;

import java.util.Arrays;
import java.util.List;

public class Team {
    public List<Employee> createDevTeam(){
        return Arrays.asList(new Employee("DEV"));
    }
}
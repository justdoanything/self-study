package book.pattern.behavioral.interator;

import java.util.Iterator;

public class IteratorPattern {
    public static void main(String[] args) {
        Team devTeam = new Team("DEV", 10);
        Team testTeam = new Team( "TEST", 3);

        Employee john = new Employee("DEV", "John");
        devTeam.addEmployee(john);
        devTeam.addEmployee(new Employee("DEV", "Anh"));
        devTeam.addEmployee(new Employee("DEV", "Dan"));
        devTeam.addEmployee(new Employee("DEV", "Hung"));
        devTeam.addEmployee(new Employee("DEV", "Tony"));

        testTeam.addEmployee(new Employee("TEST", "Anna"));
        testTeam.addEmployee(new Employee("TEST", "Drave"));
        testTeam.addEmployee(new Employee("TEST", "Polly"));
        testTeam.addEmployee(new Employee("TEST", "Zool"));
        testTeam.addEmployee(new Employee("TETS", "Chris"));

        Iterator iterator = devTeam.getEmployeeIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        iterator = testTeam.getEmployeeIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        devTeam.removeEmployee(john);
        iterator = devTeam.getEmployeeIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

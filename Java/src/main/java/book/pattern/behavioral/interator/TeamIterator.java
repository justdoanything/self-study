package book.pattern.behavioral.interator;

import java.util.Iterator;

public class TeamIterator implements Iterator<Employee> {
    private Team team;
    private int index = 0;

    public TeamIterator(Team team){
        this.team = team;
    }

    @Override
    public boolean hasNext() {
        return index < team.getEmployeeList().size();
    }

    @Override
    public Employee next() {
        return team.getEmployeeList().get(index++);
    }
}

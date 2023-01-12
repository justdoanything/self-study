package book.pattern.creational.factory.pattern;

import book.pattern.creational.factory.model.Developer;
import book.pattern.creational.factory.model.Employee;
import book.pattern.creational.factory.model.Tester;

public class FactoryPattern {
    enum EmployeeType {
        Developer,
        Tester
    }

    public static Employee getEmployee(EmployeeType employeeType, String id, String name, String department) {
        switch(employeeType){
            case Developer :
                return new Developer(id, name, department);
            case Tester :
                return new Tester(id, name, department);
            default :
                return null;
        }
    }

    public static void main(String[] args) {
        Employee developer = getEmployee(EmployeeType.Developer, "1", "Dan", "DEV");
        Employee tester = getEmployee(EmployeeType.Tester, "1", "Dan", "DEV");

        if(developer instanceof Developer)
            System.out.println("There is a developer.");
        if(tester instanceof Tester)
            System.out.println("There is a tester.");
    }
}

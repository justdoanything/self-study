package book.pattern.creational.factory.pattern;

import book.pattern.creational.factory.DeveloperFactory;
import book.pattern.creational.factory.EmployeeFactory;
import book.pattern.creational.factory.TesterFactory;
import book.pattern.creational.factory.model.Developer;
import book.pattern.creational.factory.model.Employee;
import book.pattern.creational.factory.model.Tester;

public class AbstractFactoryPattern {
    public static void main(String[] args) {
        Employee developer = EmployeeFactory.getEmployee(new DeveloperFactory("1","John","DEV"));
        Employee tester = EmployeeFactory.getEmployee(new TesterFactory("2","Dan","TEST"));

        if(developer instanceof Developer)
            System.out.println("There is a developer.");
        if(tester instanceof Tester)
            System.out.println("There is a tester.");
    }
}

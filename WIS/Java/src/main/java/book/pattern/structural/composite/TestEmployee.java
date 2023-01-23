package book.pattern.structural.composite;

/* Leaf Object */
public class TestEmployee implements Employee {
    private String name;

    public TestEmployee(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void doWork(String work) {
        System.out.println("TestEmployee " + name + " is doing " + work);
    }
}


package book.pattern.structural.composite;

/* Leaf Object */
public class DevEmployee implements Employee {
    private String name;

    public DevEmployee(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void doWork(String work) {
        System.out.println("DevEmployee " + name + " is doing " + work);
    }
}

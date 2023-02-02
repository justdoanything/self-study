package book.pattern.behavioral.template.templ;

public abstract class Pasta {
    protected void boilWater() {
        System.out.println("물을 끓인다.");
    }

    protected void putNoodle() {
        System.out.println("면을 넣는다.");
    }

    protected void pickUpNoodle() {
        System.out.println("면을 건진다.");
    }

    protected void coolNoodle() {
        System.out.println("면을 식힌다.");
    }

    protected void mixSource() {
        System.out.println("소스를 섞는다.");
    }

    protected void enjoyPasta() {
        System.out.println("파스타를 먹는다.");
    }

    protected abstract void doExtra();

    protected abstract void waitHotNoodle();

    public abstract void cookPasta();
}

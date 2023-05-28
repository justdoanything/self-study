package book.pattern.behavioral.template.template;

public class CreamPasta extends Pasta {
    @Override
    protected void doExtra() {
        System.out.println("다른 냄비에서 크림 소스를 데우고 있는다.");
    }

    @Override
    protected void waitHotNoodle() {
        System.out.println("15분 기다린다.");
    }

    @Override
    public void cookPasta() {
        System.out.println("크림 파스타를 만듭니다.");
        boilWater();
        putNoodle();
        waitHotNoodle();
        doExtra();
        pickUpNoodle();
        mixSource();
        enjoyPasta();
    }
}

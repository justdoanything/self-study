package book.pattern.behavioral.template.template;

public class TomatoPasta extends Pasta {
    @Override
    protected void doExtra() {
        System.out.println("다른 재료들을 넣는다.");
    }

    @Override
    protected void waitHotNoodle() {
        System.out.println("10분 기다린다.");
    }

    @Override
    public void cookPasta() {
        System.out.println("토마토 파스타를 만듭니다.");
        boilWater();
        putNoodle();
        waitHotNoodle();
        doExtra();
        pickUpNoodle();
        coolNoodle();
        mixSource();
        enjoyPasta();
    }
}
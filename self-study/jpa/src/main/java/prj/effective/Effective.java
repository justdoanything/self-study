package prj.effective;

public class Effective {
    public static void main(String[] args) throws Exception {
        BuilderPattern person = new BuilderPattern.Builder("YW", 20, "서울")
                                            .gender("남")
                                            .number("010")
                                            .company("LG")
                                            .family(3)
                                            .build();
    
        System.out.println(person);
    }
}

package book.effective;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        BuilderPattern person =
                new BuilderPattern.Builder("YW", 20, "서울")
                        .gender("남")
                        .number("010")
                        .company("LG")
                        .family(3)
                        .build();

        System.out.println(person);
    }
}

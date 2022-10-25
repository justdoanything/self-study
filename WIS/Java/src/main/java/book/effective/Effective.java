package book.effective;

public class Effective {
    public static void main(String[] args) throws Exception {
        Person person = new Person.Builder("YW", 20, "서울")
                .gender("남")
                .number("010")
                .company("LG")
                .family(3)
                .build();

        System.out.println(person);
    }
}

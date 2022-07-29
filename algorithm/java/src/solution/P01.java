package src.solution;

public class P01 {
    public static void main(String[] agrs) {
        String posEng = "d4";
        int posEngX = posEng.charAt(0)-'a';
        int posEngY = posEng.charAt(1)-'0';

        System.out.println(posEngX);
        System.out.println(posEngY);

        posEng.lines().map(i -> {
            if(i instanceof String){
                return i.charAt(0)-'a';
            }else{
                return i.charAt(0)-'0';
            }
        }).forEach(System.out::println);

    }
}

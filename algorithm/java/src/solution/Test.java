package src.solution;

import java.util.stream.Stream;

public class Test {
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

        int num = 8;
        String[] array = String.format("%05d", Integer.parseInt(Integer.toBinaryString(num))).split("");
        
        Stream.of(array).forEach(System.out::print);

        System.out.println(Math.abs(-4));
        System.out.println(Math.sqrt(25));
        System.out.println(Math.sqrt(-25));

        System.out.println(Math.floor(2.4));
        System.out.println(Math.ceil(2.4));
        System.out.println(Math.round(2.4));
    }
}

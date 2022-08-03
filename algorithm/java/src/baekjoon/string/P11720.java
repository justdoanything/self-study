package src.baekjoon.string;

import java.util.Arrays;
import java.util.Scanner;

public class P11720 {
    
    static int result = 0;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();

        String num = scanner.nextLine();

        Arrays.stream(num.split("")).forEach(value -> result += Integer.parseInt(value));
        System.out.println(result);
    }
}

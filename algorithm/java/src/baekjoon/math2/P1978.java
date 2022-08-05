package src.baekjoon.math2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class P1978 {
    static int result = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());
        String[] array = br.readLine().split(" ");

        Stream.of(array).forEach(value -> {
            if(Integer.parseInt(value) != 1 && isPrime(Integer.parseInt(value)))
                result++;
        });
        System.out.println(result);
    }

    private static boolean isPrime(int value){
        int root = (int)Math.sqrt((double) value);
        return IntStream.rangeClosed(2, root).noneMatch(i -> value % i == 0);
    }
}

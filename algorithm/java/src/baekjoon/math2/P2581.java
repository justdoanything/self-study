package baekjoon.math2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class P2581 {

    static int sum = 0;
    static int min = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());

        // 합과 최소값 
        for(int i=N; i>=M; i--){
            if(i==1)
                continue;
            if(isPrime(i)) {
                sum += i;
                min = i;
            }   
        }
        if(sum == 0 ){
            System.out.println("-1");
        }else {
            System.out.println(sum);
            System.out.println(min);
        }
    }

    private static boolean isPrime(int value){
        int root = (int)Math.sqrt((double) value);
        return IntStream.rangeClosed(2, root).noneMatch(i -> value % i == 0);
    }
}

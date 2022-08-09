package src.baekjoon.math1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class _P1193 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());

        int fibo = 1;
        int count = 1;

        while(true){
            if(fibo > input){
                break;
            }else {
                fibo += count;
                count++;
            }
        }

        int row = count-1;
        int rest = input-(fibo-(count-1));
        if(row % 2 == 0){
            System.out.println((1+rest) + "/" + (row-rest));
        }else {
            System.out.println((row-rest) + "/" + (1+rest));
        }
    }
}

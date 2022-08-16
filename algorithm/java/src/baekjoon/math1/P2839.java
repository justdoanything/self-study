package baekjoon.math1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class P2839 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());

        int max = input / 5;
        while(true){
            if(max == 0){
                if(input%3 == 0){
                    System.out.println(input/3);
                    break;
                }
                System.out.println("-1");
                break;
            }

            int rest = input - (5*max);
            
            if(rest%3 == 0){
                System.out.println(max + rest/3);
                break;
            }else {
                max--;
            }
        }
    }
}

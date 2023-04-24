package baekjoon.math1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class P2869 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        
        double A = Double.parseDouble(input[0]);
        double B = Double.parseDouble(input[1]);
        double V = Double.parseDouble(input[2]);

        if(V==A)
            System.out.println(1);
        else if(V-A+B <= A)
            System.out.println(2);
        else {
            int temp = (int)Math.ceil((V-A)/(A-B));
            System.out.println(temp+1);
        }
    }
}

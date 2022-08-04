package src.baekjoon.math1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class P10757 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        System.out.println(new BigInteger(input[0]).add(new BigInteger(input[1])));
    }
}

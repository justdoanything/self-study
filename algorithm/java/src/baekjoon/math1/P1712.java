package src.baekjoon.math1;

import java.util.Scanner;

public class P1712 {
    
    static int A = 0;
    static int B = 0;
    static int C = 0;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);
        C = Integer.parseInt(input[2]);

        if(B>C)
            System.out.println("-1");
        else{
            System.out.println((A / (C-B)) + 1);
        }
    }
}

package src.baekjoon.string;

import java.util.Scanner;

public class P2908 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] a = input.split(" ")[0].split("");
        String[] b = input.split(" ")[1].split("");

        String temp = a[0];
        a[0] = a[2];
        a[2] = temp;

        temp = b[0];
        b[0] = b[2];
        b[2] = temp;

        int aa = Integer.parseInt(a[0]+a[1]+a[2]);
        int bb = Integer.parseInt(b[0]+b[1]+b[2]);

        if(aa>bb){
            System.out.println(aa); 
        }else{
            System.out.println(bb);
        }
    }
}

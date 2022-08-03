package src.baekjoon.string;

import java.util.Scanner;

public class P2675 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.nextLine();
        String result = "";

        for(int i=0; i<num; i++){
            String[] array = scanner.nextLine().split(" ");
            int repeat = Integer.parseInt(array[0]);
            String str = array[1];
            for(int s=0; s<str.length(); s++){
                for(int j=0; j<repeat; j++){
                    result += str.charAt(s);
                }
            }
            System.out.println(result);
            result = "";
        }
    }
}

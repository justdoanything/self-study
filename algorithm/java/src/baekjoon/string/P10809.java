package baekjoon.string;

import java.util.Scanner;

public class P10809 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        for(int i=97; i<=122; i++){
            if(input.contains(String.valueOf((char)i)))
                System.out.print(input.indexOf((char)i) + " ");
            else
                System.out.print("-1 ");
        }
    }
}

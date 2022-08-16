package baekjoon.string;

import java.util.Scanner;

public class P5622 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int result = 0;
        for(int i=0; i<input.length(); i++){
            if(input.charAt(i) == 'Z')
                result += 10;
            else if(input.charAt(i) > 'R')
                result += 3 + (((input.charAt(i)-'A')-1) / 3);
            else
                result += 3 + (((input.charAt(i)-'A')) / 3);
        }
        
        System.out.println(result);
    }
}

package baekjoon.string;

import java.util.Scanner;

public class P1316 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.nextLine();
        
        int result = 0;
        for(int i=0; i<num; i++){
            String input = scanner.nextLine();
            String checker = "";
            boolean correct = true;

            for(int j=0; j<input.length(); j++){
                if(checker.length() > 1 && checker.contains(""+input.charAt(j))){
                    correct = false;
                    break;
                }
                
                if(j != input.length()-1 && input.charAt(j) != input.charAt(j+1))
                    checker += input.charAt(j);
            }
            if(correct)
                result++;
        }
        System.out.println(result);
    }
}

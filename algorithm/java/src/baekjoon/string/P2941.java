package baekjoon.string;

import java.util.Scanner;

public class P2941 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // c=, c-, dz=, d-, lj, nj, s=, z=
        String[] param = {"c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z="};

        int result = 0;
        for(String cro : param){
            while(input.contains(cro)){
                result++;
                input = input.replaceFirst(cro, "0");
            }
        }

        result += input.replaceAll("0","").length();
        System.out.println(result);
    }
}

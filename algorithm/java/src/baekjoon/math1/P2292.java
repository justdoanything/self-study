package baekjoon.math1;

import java.util.Scanner;

public class P2292 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();

        int layer = 2;

        int before = 2;
        int after = 7;
        if(input == 1)
            System.out.println("1");
        else{
            while(true){
                if(input >= before && input <= after){
                    System.out.println(layer);
                    break;
                }
                else{
                    before = after + 1;
                    after = after + 6*layer;
                    layer++;
                }
            }
        }
    }
}

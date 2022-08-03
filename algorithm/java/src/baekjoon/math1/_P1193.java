package src.baekjoon.math1;

import java.util.Scanner;

public class _P1193 {
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();

        int index = 1;
        // 행과열이다. i/j 를 출력하면된다.
        index = 1;
        // 전체 탐색을 위한 변수 c , 범위는 row+col
        for(int c=0; c<=100; c++){
            for(int i=0; i<100; i++){
                for(int j=0; j<100; j++){
                    // i와 j의 합이 c와 같은 곳에서만 출력
                    if(i+j == c){
                        if(index == input){
                            System.out.println((j+1) + "/" + (i+1));
                            return;
                        }
                        index++;
                    }
                }
            }
        }
    }
}

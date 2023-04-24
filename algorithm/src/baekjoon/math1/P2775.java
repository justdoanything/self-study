package baekjoon.math1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class P2775 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = Integer.parseInt(br.readLine());

        for(int index=0; index<num; index++){
            int k = Integer.parseInt(br.readLine());
            int n =Integer.parseInt(br.readLine());

            int value = 1;
            int[][] array = new int[k+1][n];
            for(int i=0; i<k+1; i++){
                for(int j=0; j<n; j++){
                    if(i==0){
                        array[i][j] = value++;
                    }else {
                        if(j==0) {
                            array[i][j] = 1;
                        }else {
                            array[i][j] = array[i-1][j] + array[i][j-1];
                        }
                    }
                }
            }
            System.out.println(array[k][n-1]);
        }
    }
}

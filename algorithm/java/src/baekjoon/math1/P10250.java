package src.baekjoon.math1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class P10250 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = Integer.parseInt(br.readLine());

        for(int index=0; index<num;index++){
            String[] arr = br.readLine().split(" ");

            int high = Integer.parseInt(arr[0]);
            int width = Integer.parseInt(arr[1]);
            int person = Integer.parseInt(arr[2]);

            int floor = person%high == 0 ? high : person%high;
            int ho = floor == high ? (person/high) : (person/high) + 1;

            System.out.println(floor + String.format("%02d", ho));    
        }
    }
}   

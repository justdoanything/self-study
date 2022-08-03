package src.baekjoon.string;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class P1157 {
    static int max = 0;
    static String maxKey = "";
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        input = input.toUpperCase();
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0; i<input.length(); i++){
            if(!map.containsKey(input.charAt(i)))
                map.put(input.charAt(i), 1);
            else
                map.put(input.charAt(i), map.get(input.charAt(i)) + 1);
        }

        
        for(Character key : map.keySet()){
            if(map.get(key) > max){
                max = map.get(key);
                maxKey = String.valueOf(key);
            }
        }

        if(map.values().stream().filter((value) -> { return value == max;}).count() > 1)
            System.out.println("?");
        else
            System.out.println(maxKey);
    }
}

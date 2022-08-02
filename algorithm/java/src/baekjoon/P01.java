package src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class P01 {
    
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String str = r.readLine();
        r.close();
        System.out.println(str.trim().split(" ").length);
    }
}

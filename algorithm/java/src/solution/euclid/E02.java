package solution.euclid;

import java.math.BigInteger;

public class E02 {
    
    public static void main(String[] args) {
        int w = 160;
        int h = 180;
        System.out.println(solution(w, h)); //34

        w = 320;
        h = 220;
        System.out.println(solution(w, h)); //54

    }   

    public static int solution(int w, int h){
        int answer = 0;
        
        BigInteger wi = BigInteger.valueOf(w);
        BigInteger hi = BigInteger.valueOf(h);
        
        int gcd = wi.gcd(hi).intValue();

        answer = (w/gcd + h/gcd) * 2;

        return answer;
    }
}

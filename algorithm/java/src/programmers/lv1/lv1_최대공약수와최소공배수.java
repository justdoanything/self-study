package programmers.lv1;

import java.math.BigInteger;

public class lv1_최대공약수와최소공배수 {
    public int[] solution(int n, int m) {
        int[] answer = new int[2];

        BigInteger bn = BigInteger.valueOf(n);
        BigInteger bm = BigInteger.valueOf(m);

        int gcd = bn.gcd(bm).intValue();
        answer[0] = gcd;
        answer[1] = n*m/gcd;

        return answer;
    }
}

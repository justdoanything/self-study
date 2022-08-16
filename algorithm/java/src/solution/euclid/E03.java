package solution.euclid;

import java.math.BigInteger;

public class E03 {
    
    public static void main(String[] args){
        
        int a = 25;
        int b = 36;

        System.out.println(gcd(a,b));
        System.out.println(lcm(a,b));

        // gcd
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigB = BigInteger.valueOf(b);
                
        int gcd = bigA.gcd(bigB).intValue();

        // lcm
        int lcm = a*b / gcd;

        System.out.println(gcd);
        System.out.println(lcm);
    }

    private static int gcd(int a, int b){
        while(b != 0){
          int r = a%b;
          a = b;
          b = r;
        }
        return a;
      }
      // 최소공배수(lcm)
      public static int lcm(int a, int b){
        return a*b/gcd(a,b);
      }
}

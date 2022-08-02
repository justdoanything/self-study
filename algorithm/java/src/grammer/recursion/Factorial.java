package src.grammer.recursion;

public class Factorial {

    public static void main(String[] args) {
        int n = 5;
        int r = 3;

        System.out.println(factorial(n));
        System.out.println(permutation(n, r));
        System.out.println(combination(n, r));
    }
    
    private static int factorial(int n) {
        if( n == 1)
            return 1;
        else
            return n * factorial(n - 1);
    }

    private static int permutation(int n, int r) {
        if(r == 1)
            return n;
        else
            return n * permutation(n - 1, r - 1);
    }

    private static int combination(int n, int r) {
        return permutation(n, r) / factorial(r);
    }
    
}

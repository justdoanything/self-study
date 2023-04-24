package programmers.lv1;

public class lv1_정수제곱근판별 {

    public static void main(String[] args) {
        System.out.println(Math.sqrt(121));

    }
    public static long solution(long n) {
        return Math.sqrt(n) % 1 == 0.0 ? (long) Math.pow((Math.sqrt(n)+1),2) : -1;
    }
}

package programmers.lv1;

public class lv1_하샤드수 {
    public static void main(String[] args) {
        solution(18);
    }
    public static boolean solution(int x) {
        int sum = 0;
        int num = x;
        while (num != 0){
            sum += num % 10;
            num /= 10;
        }
        return x % sum == 0;
    }
}

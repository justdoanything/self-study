package programmers.lv1;

import java.util.ArrayList;
import java.util.List;

public class lv1_소수만들기_SummerWinterCoding {

    public static void main(String[] args) {

        System.out.println(solution(new int[]{1, 2, 7, 6, 4}));
    }

    public static int solution(int[] nums) {
        int answer = 0;
        int sum = 0;
        String check = "";
        List<String> checked = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    sum = nums[i] + nums[j] + nums[k];
                    check = nums[i]+","+nums[j]+","+nums[k];
                    if (!checked.contains(check) && isPrime(sum)) {
                        checked.add(check);
                        answer++;
                    }
                }
            }
        }

        return answer;
    }

    public static boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

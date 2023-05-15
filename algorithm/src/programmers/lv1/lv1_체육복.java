package programmers.lv1;


import java.util.Arrays;

public class lv1_체육복 {

    public static void main(String[] args) {
        System.out.println(solution(5, new int[]{4, 5}, new int[]{3, 4}));

    }

    public static int solution(int n, int[] lost, int[] reserve) {
        Arrays.sort(lost);
        Arrays.sort(reserve);
        int minus = lost.length;
        for (int i = 0; i < reserve.length; i++) {
            for (int j = 0; j < lost.length; j++) {
                if (lost[j] == reserve[i]) {
                    minus -= 1;
                    lost[j] = -1;
                    reserve[i] = -1;
                    break;
                }
            }
        }
        for (int i = 0; i < reserve.length; i++) {
            for (int j = 0; j < lost.length; j++) {
                if (lost[j] != -1 && reserve[i] != -1 && (lost[j] == reserve[i] - 1 || lost[j] == reserve[i] + 1)) {
                    minus -= 1;
                    lost[j] = -1;
                    reserve[i] = -1;
                    break;
                }
            }
        }
        return n - minus;
    }
}

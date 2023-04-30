package programmers.lv1;

import java.util.Arrays;

public class lv1_비밀지도_2018KakaoBlind {
    public static void main(String[] args) {
        for(int i=0; i<5; i++){
            System.out.println(solution(5, new int[]{0, 20, 28, 18, 11}, new int[]{0, 1, 21, 17, 28})[i]);
        }
    }

    public static String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        Arrays.fill(answer, "");

        for (int i = 0; i < n; i++) {
            String temp1 = String.format("%0" + n + "d", Long.parseLong(Long.toBinaryString(arr1[i])));
            String temp2 = String.format("%0" + n + "d", Long.parseLong(Long.toBinaryString(arr2[i])));
            for (int j = 0; j < n; j++) {
                if (temp1.charAt(j) == '1' || temp2.charAt(j) == '1')
                    answer[i] += "#";
                else
                    answer[i] += " ";
            }
        }
        return answer;
    }
}

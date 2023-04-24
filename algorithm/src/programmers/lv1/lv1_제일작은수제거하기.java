package programmers.lv1;

import java.util.stream.IntStream;

public class lv1_제일작은수제거하기 {
    public lv1_제일작은수제거하기() {
    }

    public int[] solution(int[] arr) {
        if(arr.length < 2)
            return new int[]{-1};

        int[] answer = new int[arr.length-1];
        int min = IntStream.of(arr).min().getAsInt();
        int index = 0;
        for(int num : arr) {
            if(num != min)
                answer[index++] = num;
        }
        return answer;
    }
}

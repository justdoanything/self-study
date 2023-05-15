package programmers.lv1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lv1_실패율_2019KakaoBlind {
    public static void main(String[] args) {
        for (int i : solution(5, new int[]{2, 1, 2, 1, 2, 1, 3, 3})) {
            System.out.println(i);
        }

    }

    public static int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        int remainPlayer = stages.length;
        double[] stageAndPlayer = new double[N];
        List<Double> failWithIndex = new ArrayList<>();

        for (int stage = 0; stage < N; stage++) {
            int thisStagePlayer = 0;
            for (int index = 0; index < stages.length; index++) {
                if (stage + 1 == stages[index])
                    thisStagePlayer++;
            }

            if (remainPlayer == 0) {
                stageAndPlayer[stage] = 0;
                failWithIndex.add(0.0);
            } else {
                stageAndPlayer[stage] = (double) thisStagePlayer / remainPlayer;
                failWithIndex.add((double) thisStagePlayer / remainPlayer);
            }

            remainPlayer -= thisStagePlayer;
        }

        Arrays.sort(stageAndPlayer);
        int answerIndex = 0;
        for (int index = N - 1; index >= 0; index--) {
            int find = failWithIndex.indexOf(stageAndPlayer[index]);
            failWithIndex.remove(find);
            failWithIndex.add(find, stageAndPlayer[index] + 1);
            answer[answerIndex++] = find + 1;
        }
        return answer;
    }
}

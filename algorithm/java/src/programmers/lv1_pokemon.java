package programmers;

import java.util.Arrays;
import java.util.stream.Collectors;

public class lv1_pokemon {
    public int solution(int[] nums) {
        int answer = 0;
        int pick = nums.length / 2;


        int[] picked = new int[200001];

        picked[nums[0]] = 1;
        int diff = 1;

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1] && picked[nums[i + 1]] == 0) {
                picked[nums[i + 1]] = 1;
                diff++;
            }
        }
        if (pick < diff)
            answer = pick;
        else
            answer = diff;
        return answer;
    }

    public int bestSolution(int[] nums) {
        return Arrays.stream(nums)
                .boxed()
                .collect(Collectors.collectingAndThen(Collectors.toSet(),
                        phonekemons -> Integer.min(phonekemons.size(), nums.length / 2)));
    }
}

package programmers.lv1;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class lv1_예산_SummerWinterCoding {
    public int solution(int[] d, int budget) {
        int answer = 0;

        List<Integer> list = IntStream.of(d).boxed().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for(int n : list) {
            if(budget - n >= 0){
                budget = budget - n;
                answer++;
            }
        }

        return answer;
    }
}

package programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class lv1_같은숫자는싫어 {
    public int[] solution(int []arr) {
        List<Integer> list = new ArrayList<>();
        int before = -1;
        for(int i : arr){
            if(before != i)
                list.add(i);
            before = i;
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
}

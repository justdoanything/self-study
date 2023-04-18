package programmers.lv1;

import java.util.ArrayList;
import java.util.List;

public class lv1_같은숫자는싫어 {
    public int[] solution(int []arr) {
        List<Integer> list = new ArrayList<>();
        int before = -1;
        for(int i : arr){
            if(before != i)
                list.add(i);
            before = i;
        }
        list.toArray(new Integer[0]);
        return list.stream().mapToInt(i -> i).toArray();
    }
}

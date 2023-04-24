package programmers.lv1;

import java.util.stream.IntStream;

public class lv1_평균구하기 {
    public double solution(int[] arr) {
        return IntStream.of(arr).average().getAsDouble();
    }
}

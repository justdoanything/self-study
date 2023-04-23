package programmers.lv1;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lv1_정수내림차순으로배치하기 {
    public long solution(long n) {
        return Long.parseLong(Stream.of(String.valueOf(n).split(""))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining()));
    }
}

package programmers.lv1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class lv1_문자열내림차순으로배치하기 {
    public String solution(String s) {
        List<String> list = Arrays.asList(s.split(""));
        list.sort((str1, str2) -> {
            if(str1.compareTo(str2) > 0)
                return -1;
            else
                return 1;
        });
        return list.stream().collect(Collectors.joining());
//        return Stream.of(s.split(""))
//                .sorted(Comparator.reverseOrder())
//                .collect(Collectors.joining());
    }
}

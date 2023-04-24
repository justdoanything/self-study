package programmers.lv1;

import java.util.Arrays;
import java.util.List;

public class lv1_문자열내마음대로정렬하기 {
    public static void main(String[] args) {
        solution(new String[]{"sun", "bed", "car"}, 1);
    }
    public static String[] solution(String[] strings, int n) {
        List<String> list = Arrays.asList(strings);
        list.sort((str1, str2) -> {
            if(str1.charAt(n) > str2.charAt(n))
                return 1;
            else if(str1.charAt(n) == str2.charAt(n))
                return str1.compareTo(str2);
            else
                return -1;
        });
        return list.toArray(new String[0]);
    }
}

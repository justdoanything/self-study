package programmers.lv1;

public class lv1_이상한문자만들기 {
    public String solution(String s) {
        String answer = "";
        int index = 0;
        for (String str : s.split("")) {
            if (str.equals(" ")) {
                answer += str;
                index = 0;
                continue;
            } else if (index % 2 == 0) {
                answer += str.toUpperCase();
            } else {
                answer += str.toLowerCase();
            }
            index++;
        }
        return answer;
    }
}

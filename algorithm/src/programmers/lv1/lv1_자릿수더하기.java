package programmers.lv1;

public class lv1_자릿수더하기 {
    public int solution(int n) {
        int answer = 0;
        for(String str : String.valueOf(n).split("")){
            answer += Integer.parseInt(str);
        }
        return answer;
    }
}

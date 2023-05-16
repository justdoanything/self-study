package programmers.lv1;

public class lv1_내적 {
    public int solution(int[] a, int[] b) {
        int answer = 0;
        for(int i=0; i<a.length; i++){
            answer += a[i] * b[i];
        }
        return answer;
    }
}

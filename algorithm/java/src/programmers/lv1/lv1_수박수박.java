package programmers.lv1;

public class lv1_수박수박 {
    public String solution(int n) {
        String answer = "";
        for(int i=0; i<n; i++){
            if(i%2==0)
                answer += "수";
            else
                answer += "박";
        }
        return answer;
    }
}


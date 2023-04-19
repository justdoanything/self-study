package programmers.lv1;

public class lv1_약수의합 {
    public int solution(int n) {
        int answer = 0;

        for(int i=1; i<=Math.sqrt(n); i++){
            if(n%i == 0){
                if(i*i==n)
                    answer += i;
                else{
                    answer += i;
                    answer += n/i;
                }
            }
        }

        return answer;
    }
}

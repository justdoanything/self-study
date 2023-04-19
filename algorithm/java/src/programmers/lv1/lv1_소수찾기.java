package programmers.lv1;

public class lv1_소수찾기 {
    public static void main(String[] args) {
        System.out.println(solution(10));
    }
    public static int solution(int n) {
        if(n==2)
            return 1;

        int answer = 1;
        for(int i=3; i<=n; i++){
            boolean flag = true;
            for(int j=2; j<=Math.sqrt(i); j++){
                if(i%j == 0){
                    flag = false;
                    break;
                }
            }
            if (flag)
                answer++;
        }
        return answer;
    }
}

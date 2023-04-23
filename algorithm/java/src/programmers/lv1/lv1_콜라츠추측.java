package programmers.lv1;

public class lv1_콜라츠추측 {
    public static void main(String[] args) {
        System.out.println(solution(626331));
    }
    public static int solution(int num) {
        int answer = -1;
        long number = num;

        for(int i=0; i<=500; i++){
            if(number == 1){
                answer = i;
                break;
            }
            number = number % 2 == 0 ? number/2 : number*3+1;
        }
        return answer;
    }
}

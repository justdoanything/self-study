package programmers.lv1;

public class lv1_부족한금액계산하기 {
    public long solution(int price, int money, int count) {
        long playAmount = 0;
        for(int i=1; i<=count; i++){
            playAmount += price * i;
        }

        long answer = money - playAmount;
        return answer > 0 ? 0 : answer*-1;
    }
}

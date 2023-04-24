package programmers.lv1;

public class lv1_자연수뒤집어배열로만들기 {
    public int[] solution(long n) {
        int length = String.valueOf(n).length();
        int[] answer = new int[length];
        int index = 0;
        while (true) {
            answer[index++] = (int) (n % 10);
            n = n/10;
            if(index == length) break;
        }

        return answer;
    }
}

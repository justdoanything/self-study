package programmers.lv1;

import java.util.Arrays;

public class lv1_체육복 {

    public static void main(String[] args) {
        System.out.println(solution(5, new int[]{1,2,3}, new int[]{2,3,4}));

    }
    public static int solution(int n, int[] lost, int[] reserve) {
        int dupl = (int) Arrays.stream(reserve).flatMap(i -> Arrays.stream(lost).filter(j -> i==j)).count();

        int lostCount = lost.length - dupl;
        int reserveCount = reserve.length - dupl;

        int answer = 0;
        if(lostCount < reserveCount)
            answer = n;
        else
            answer = n - lostCount + reserveCount;

        return n < answer ? n : answer;
    }
}


// 5
// { 1, 2, 3 }
// { 2, 3, 4 }
// dupl = 3

// 10 - 6 + 3 + 4 - 3

// 5 - 1 + 2

package programmers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class lv1_2016 {
    public static void main(String[] args) {
        System.out.println(solution(1, 1));
    }

    public static String solution(int a, int b) {
        String answer = "";
        String[] days = {"FRI","SAT","SUN","MON","TUE","WED","THU"};

        LocalDate date1 = LocalDate.of(2016, 1, 1);
        LocalDate date2 = LocalDate.of(2016, a, b);
        int dayDiff = (int) ChronoUnit.DAYS.between(date1, date2)%7;

        answer = days[dayDiff];
        return answer;
    }

    public String bestSolution(int a, int b) {
        String answer = "";

        int[] c = {31,29,31,30,31,30,31,31,30,31,30,31};
        String[] MM ={"FRI","SAT","SUN","MON","TUE","WED","THU"};
        int Adate = 0;
        for(int i = 0 ; i< a-1; i++){
            Adate += c[i];
        }
        Adate += b-1;
        answer = MM[Adate %7];

        return answer;
    }
}

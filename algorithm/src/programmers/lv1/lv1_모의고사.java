package programmers.lv1;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lv1_모의고사 {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{1,2,3,4,5}));
    }

    public static int[] solution(int[] answers) {
        List<String> a = Arrays.asList("12345".repeat(answers.length/5+1).split(""));
        List<String> b = Arrays.asList("21232425".repeat(answers.length/5+1).split(""));
        List<String> c = Arrays.asList("3311224455".repeat(answers.length/5+1).split(""));

        int correct[] = new int[3];

        for(int i=0; i<answers.length; i++){
            if(answers[i] == Integer.parseInt(a.get(i)))
                correct[0] += 1;
            if(answers[i] == Integer.parseInt(b.get(i)))
                correct[1] += 1;
            if(answers[i] == Integer.parseInt(c.get(i)))
                correct[2] += 1;
        }

        int max = 0;
        for(int i=0; i<correct.length; i++){
            if(correct[i] > max)
                max = correct[i];
        }

        List<Integer> answer = new ArrayList<>();
        for(int i=0; i<correct.length; i++){
            if(correct[i] == max){
                answer.add(i+1);
            }

        }
        return answer.stream().mapToInt(i->i).toArray();
    }
}

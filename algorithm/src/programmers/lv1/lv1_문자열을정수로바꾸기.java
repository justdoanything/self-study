package programmers.lv1;

public class lv1_문자열을정수로바꾸기 {
    public int solution(String s) {
        if(s.startsWith("-")){
            int num = Integer.parseInt(s.substring(1));
            return num - num*2;
        } else if(s.startsWith("+"))
            return Integer.parseInt(s.substring(1));
        else
            return Integer.parseInt(s);
    }
}

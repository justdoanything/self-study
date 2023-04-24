package programmers.lv1;

public class lv1_문자열다르기기본 {
    public static void main(String[] args) {
        System.out.println(solution(""));
    }
    public static boolean solution(String s) {
        if(s.length()==4 || s.length()==6){
            for(String str : s.split("")){
                if((str.charAt(0) >= 'a' && str.charAt(0) <= 'z') || (str.charAt(0) >= 'A' && str.charAt(0) <= 'Z'))
                    return false;
            }
            return true;
        }else
            return false;
    }
}

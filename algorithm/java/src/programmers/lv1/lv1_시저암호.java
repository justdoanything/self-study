package programmers.lv1;

public class lv1_시저암호 {
    public static void main(String[] args) {
        System.out.println(solution("abc", 25));
    }
    public static String solution(String s, int n) {
        String answer = "";
        for (String str : s.split("")) {
            if (str.equals(" ")) {
                answer += " ";
            } else {
                int next = str.charAt(0) + n;
                if(str.charAt(0) >= 'a' && str.charAt(0) <= 'z'){
                    if(next > 'z')
                        answer += (char)(next-26);
                    else
                        answer += (char)(next);
                }else {
                    if(next > 'Z')
                        answer += (char)(next-26);
                    else
                        answer += (char)(next);
                }
            }
        }
        return answer;
    }
}

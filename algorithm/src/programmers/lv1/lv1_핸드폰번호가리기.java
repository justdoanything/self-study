package programmers.lv1;

public class lv1_핸드폰번호가리기 {
    public String solution(String phone_number) {
        return "*".repeat(phone_number.length()-4) + phone_number.substring(phone_number.length()-4);
    }
}

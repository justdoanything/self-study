package programmers.lv1;

public class lv1_문자열내p와y의개수 {
    public boolean solution(String s) {
        int p = 0;
        int y = 0;
        for(String str : s.split("")){
            if(str.toLowerCase().equals("p"))
                p++;
            else if(str.toLowerCase().equals("y"))
                y++;
        }
        return p == y;
    }
}

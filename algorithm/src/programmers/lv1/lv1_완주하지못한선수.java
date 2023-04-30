package programmers.lv1;

import java.util.HashMap;

public class lv1_완주하지못한선수 {
    public static void main(String[] args) {
        System.out.println(solution(new String[]{"leo", "kiki", "eden"}, new String[]{"eden", "kiki"}));
    }
    public static String solution(String[] participant, String[] completion) {

        HashMap<String, Integer> map = new HashMap<>();
        for(String s : participant) {
            if(map.containsKey(s)){
                map.put(s, map.get(s)+1);
            }else{
                map.put(s, 1);
            }
        }

        for(String s : completion) {
            Integer integer = map.get(s) == 1 ? map.remove(s) : map.put(s, map.get(s) - 1);
        }

        return map.keySet().iterator().next();
    }
}

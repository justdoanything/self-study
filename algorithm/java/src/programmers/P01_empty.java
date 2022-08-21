package programmers;

import java.util.HashMap;

class P01_empty {
    public char solution(String s) {

        HashMap<Character, Integer> hash = new HashMap<>();

        int max_num = 0;
        char max_ch = ' ';

        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);

            if (!hash.containsKey(ch)) 
                /**/hash.put(ch,1);
            
            hash.put(ch, hash.get(ch) + /**/1);
            
            if (hash.get(ch) > max_num) {
                max_num = /**/hash.get(ch);
                max_ch = /**/ch;
            }           
        }

        return max_ch;
    }
}
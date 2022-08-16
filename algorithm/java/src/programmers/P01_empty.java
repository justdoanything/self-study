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

/*
문자열 하나가 주어졌을 때, 가장 많이 등장하는 문자를 구해주는 함수를 만들려고 합니다. 문자열에서 가장 많이 등장하는 문자는 반드시 한 종류만 주어진다고 했을 때, 주어진 초기 코드의 빈칸을 채워 코드를 완성 시켜주세요.

제한사항

문자들은 알파벳 대,소문자 또는 숫자로 주어집니다.
문자열의 길이 : 1 <= 문자열의 길이 <= 10,000

테스트 1
입력값 〉	"ABVAAAABBB"
기댓값 〉	"A"
실행 결과 〉	테스트를 통과하였습니다.
테스트 2
입력값 〉	"2312321238887744777777"
기댓값 〉	"7"
실행 결과 〉	테스트를 통과하였습니다.

*/
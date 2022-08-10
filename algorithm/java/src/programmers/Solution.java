package src.programmers;

import java.util.regex.Pattern;

class Solution {
    public boolean solution(String ID) {
        boolean answer = false;

        if(!ID.contains("\\.")){
            String name = ID;

            if(name.length() >= 1 && name.length() <= 10){
                if(Pattern.matches("^[a-zA-Z]*$", name)){
                    answer = true;
                }
            }

        }else {
            String[] str = ID.split("\\.");

            if(str.length > 2)
                answer = false;
            else {
                String name = str[0];
                String sung = str[1];
    
                if(name.length() > 0 && sung.length() > 0 && name.length() >= 1 && name.length() <= 10 && sung.length() >= 0 && sung.length() <= 5){
                    if(Pattern.matches("^[a-zA-Z]*$", name) && Pattern.matches("^[a-zA-Z]*$", sung)){
                        answer = true;
                    }
                }
            }
        }
		
        return answer;
    }
}
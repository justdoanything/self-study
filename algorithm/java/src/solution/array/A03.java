package src.solution.array;

public class A03 {

    // 세로 글자 읽기
    public static void main(String[] agrs) {
        String[] data = {"AABCDD", "afzz", "09121", "a8EWg6", "P5h3kx"};

        System.out.println(solution(data));
    }

    private static String solution(String[] data) {
        String result = "";

        // 최대 길이 찾기
        int maxLength = 0;
        for(int i=0; i<data.length; i++){
            if(maxLength < data[i].length())
                maxLength = data[i].length();
        }

        // 세로로 읽기
        for(int i=0; i<maxLength; i++){
            for(int j=0; j<data.length; j++){
                if(i < data[j].length())
                    result += data[j].charAt(i);
            }
        }

        return result;
    }
}

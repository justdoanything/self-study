package programmers.lv1;

public class lv1_다트게임_2018KakaoBlind {
    public static void main(String[] args) {
        System.out.println(solution("1S2D*3T"));
        System.out.println(solution("1D2S#10S"));
        System.out.println(solution("1D2S0T"));
        System.out.println(solution("1S*2T*3S"));
        System.out.println(solution("1D#2S*3S"));

    }

    public static int solution(String dartResult) {
        int answer = 0;

        String[] arr = dartResult.split("");
        int now = 0;
        int before = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("S") || arr[i].equals("D") || arr[i].equals("T")) {
                if (i + 1 < arr.length && (arr[i + 1].equals("#") || arr[i + 1].equals("*"))) {
                    if (arr[i + 1].equals("#")) {
                        if (i - 2 >= 0 && arr[i - 1].equals("0") && arr[i - 2].equals("1")) {
                            now = calculate(10, arr[i]) * -1;
                        } else {
                            now = calculate(Integer.parseInt(arr[i - 1]), arr[i]) * -1;
                        }
                        answer += now;
                        before = now;
                    } else {
                        if (i - 2 >= 0 && arr[i - 1].equals("0") && arr[i - 2].equals("1")) {
                            now = calculate(10, arr[i]) * 2;
                        } else {
                            now = calculate(Integer.parseInt(arr[i - 1]), arr[i]) * 2;
                        }
                        answer += now + before;
                        before = now;
                    }
                } else {
                    if (i - 2 >= 0 && arr[i - 1].equals("0") && arr[i - 2].equals("1")) {
                        now = calculate(10, arr[i]);
                    } else {
                        now = calculate(Integer.parseInt(arr[i - 1]), arr[i]);
                    }
                    answer += now;
                    before = now;
                }
            } else {
                continue;
            }
        }
        return answer;
    }

    public static int calculate(int score, String bonus) {
        int result = 0;
        if (bonus.equals("S")) {
            result = score;
        } else if (bonus.equals("D")) {
            result = score * score;
        } else if (bonus.equals("T")) {
            result = score * score * score;
        }
        return result;
    }
}

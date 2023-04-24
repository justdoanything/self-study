package solution.search;

public class S01 {

    // 배열 안에 있는 값을 +,- 해서 target의 수를 만들 수 있는 경우의 수를 구해라.
    static int targetValue;
    static int array[];
    static int N;
    static int cnt = 0;
    public static void main(String[] args) {
        int[] numbers = {1,1,1,1,1};
        int target = 3;
        System.out.println(solution(numbers, target));  // 5

        numbers = new int[]{1,6,2,3,-2,-1-9};
        target = 0;
        System.out.println(solution(numbers, target));  // 10

        numbers = new int[]{1,1,1};
        target = 1;
        System.out.println(solution(numbers, target));  // 12
    }   
    
    private static int solution(int[] numbers, int target) {
        int result = 0;

        targetValue = target;
        array = numbers;
        N = numbers.length;

        dfs(0, 0);

        result = cnt;

        return result;
    }

    private static void dfs(int index, int value) {
        // 종료하는 조건
        if(index == N && targetValue == value) {
            cnt++;
            return;
        }
        if(index == N) {
            return;
        }

        dfs(index+1, value + array[index]);
        dfs(index+1, value - array[index]);
    }
}

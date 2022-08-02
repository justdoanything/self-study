package src.solution.search;

public class S02 {
    // [0,0]에서 [n,n]까지 갈 수 있는 최단거리를 구하라.

    public static void main(String[] args) {
        int[][] road = new int[][] {
            {1,1,1,1,1,1},
            {0,0,1,0,0,1},
            {1,1,1,0,1,1},
            {1,0,0,0,1,0},
            {1,1,1,0,1,0},
            {0,0,1,1,1,1}
        };
        System.out.println(solution(road)); // 12

        road = new int[][] {
            {1,1,1,0,0,1,1,1,1},
            {0,0,1,1,1,1,1,1,1},
            {0,0,1,1,1,0,0,0,1},
            {0,0,1,0,1,0,0,0,1},
            {0,0,1,0,1,1,0,0,1},
            {0,0,1,0,0,1,1,0,1},
            {0,0,1,0,0,0,1,1,1},
            {0,0,1,1,1,1,1,1,1}
        };
        System.out.println(solution(road)); // 15
    }

    private static int solution(int[][] road){
        int result = 0;

        return result;
    }
}

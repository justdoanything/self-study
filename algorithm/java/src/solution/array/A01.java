package src.solution.array;

public class A01 {
    // 2차원 행렬이 있을 때, 중간 기준 180도 회전한 배열과 대각선 기준으로 180도 회전한 배열을 합치고 각 인덱스의 합을 10으로 나눈 배열을 생성하라.
    public static void main(String[] agrs) {

        int[][] data = {
            {3,7,1,9}
            ,{3,5,2,8}
            ,{6,4,7,1}
            ,{5,7,5,4}
        };
        printArray(solution(data));

        data = new int[][]{
            {6,3,7,2,9}
            ,{8,5,3,6,7}
            ,{1,6,8,7,1}
            ,{4,5,7,9,4}
            ,{7,6,3,7,5}
        };
        printArray(solution(data));
    }

    public static void printArray(int[][] array) {
        for(int i=0; i<array.length; i++){
            for(int j=0; j<array[0].length; j++){
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] solution(int[][] data) {
        int[][] result = new int[data.length][data.length];
        
        int n = data.length;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                result[i][j] = (data[i][n-1-j] + data[n-1-j][n-1-i]) % 10;
            }
        }
        
        return result;
    }
}

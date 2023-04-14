package programmers;

public class Main {
    public static int[] solution(int m, int n, int[][] picture) {
        int numberOfArea = 0;
        int maxSizeOfOneArea = 0;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                int nextX = i + dx[i]*1;
                int nextY = j + dy[i]*1;

                if(nextX >=0 && nextX < picture.length && nextY >= 0 && nextY < picture[0].length){
                    if(picture[i][j] == picture[i][nextX] && picture[i][j] == picture[nextY][j])
                        numberOfArea++;

                }
            }
        }

        int[] answer = new int[2];
        answer[0] = numberOfArea;
        answer[1] = maxSizeOfOneArea;

        return answer;
    }

    public static void main(String[] args) {
        int[][] temp = {
                {1, 1, 1, 0},
                {1, 2, 2, 0},
                {1, 0, 0, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 3},
                {0, 0, 0, 3}};
        int[] answer = solution(6, 4, temp);
        System.out.println(answer[0]); // [4, 5]
        System.out.println(answer[1]); // [4, 5]
    }
}

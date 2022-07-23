package src.grammer.search;

public class BackTrackingByMe {
    
    // N Queen 문제
    // 체스판에서 Queen은 같은 행과 열에서 1개만 있을 수 있고 대각선에 인접(1칸)할 수 없다.
    // 위 경우가 부합되는 경우의 수를 구하시오
    
    static int map[][];
    static int n;
    static int ans;
    static int count = 0;
    
    public static void main(String[] args) {
        n = 4;

        for(int index=0; index<n; index++){
            map = new int[n][n];
            map[0][index] = 1;
            System.out.println("======시작");
            printArray(map);

            dfs(1);
        }
        System.out.println("정답 : " + ans);
        System.out.println("탐색 횟수 : " + count);
    }

    public static void dfs(int row){
        //현재 열이 체스판을 넘어섰다면
        if(row == 4){
            System.out.println("==========정답");
            printArray(map);
            ++ans;
        } else{
            for(int col=0; col<n; col++){
                // 유망한 노드 검초
                count++;
                if(isPossible(row, col)){
                    // 서브 트리로 이동 (해당 노드의 화위 노드)
                    map[row][col] = 1;
                    col = n;
                    System.out.println("=============temp");
                    printArray(map);
                    dfs(row + 1);
                }else {
                    map[row][col] = 2;
                    System.out.println("=============temp");
                    printArray(map);
                }
                
            }
        }
        
    }

    public static boolean isPossible(int row, int col) {
        // 같은 열에 1이 있는지 확인
        for(int i=row-1; i>=0; i--){
            if(map[i][col] == 1)
                return false;
        }

        // 대각선 검사
        if(move8Ways(map, row, col, 1, n))
            return false;

        return true;
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

    private static boolean move8Ways(int[][] map, int posX, int posY, int num, int N){
        int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1};
        
        for(int d=1; d<=num; d++){
            for(int i=0; i<8; i++){
                int nextX = posX + dx[i]*d;
                int nextY = posY + dy[i]*d;

                if(nextX >= 0 && nextX < N 
                    && nextY >= 0 && nextY < N){
                    if(map[nextX][nextY] == 1)
                        return true;
                }
            }
        }
        return false;
    }
}

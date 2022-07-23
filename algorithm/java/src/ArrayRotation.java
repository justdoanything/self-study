package src;

public class ArrayRotation {
    
    /** Key Point **/
    
    // 1. 시계방향 - 상하좌우
    int[] dx4 = {-1, 1, 0, 0};
    int[] dy4 = {0, 0, -1, 1};

    // 2. 시계방향 - 상하좌우대각선
    int[] dx8 = { -1, -1, 0, 1, 1, 1, 0, -1};
    int[] dy8 = { 0, 1, 1, 1, 0, -1, -1, -1};

    // 3. 문자열을 index로 변환
    String posEng = "d4";
    int posEngX = posEng.charAt(0)-'a';
    int posEngY = posEng.charAt(1)-'0';
    
    public static void main(String[] args){
        int N = 7;
        int[][]  map = new int[N][N];
        int posX = 3;
        int posY = 3;
        int num = 1;

        map[posX][posY] = 1;
        System.out.println("================ START");
        Array.printArray(map);

        // move4Ways(map, posX, posY, num, N);

        // move8Ways(map, posX, posY, num, N);

        //move4WaysDirectly(map, posX, posY, num, N);

        move8WaysDirectly(map, posX, posY, num, N);
        
    }

    private static void move4Ways(int[][] map, int posX, int posY, int num, int N){
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        System.out.println("================ 상하좌우");
        for(int d=1; d<=num; d++){
            for(int i=0; i<4; i++){
                int nextX = posX + dx[i]*d;
                int nextY = posY + dy[i]*d;

                if(nextX >= 0 && nextX < N 
                    && nextY >= 0 && nextY < N){
                    map[nextX][nextY] = 1;
                }
            }
        }
        Array.printArray(map);
    }

    private static void move8Ways(int[][] map, int posX, int posY, int num, int N){
        int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1};

        System.out.println("================ 상하좌우대각선");
        for(int d=1; d<=num; d++){
            for(int i=0; i<8; i++){
                int nextX = posX + dx[i]*d;
                int nextY = posY + dy[i]*d;

                if(nextX >= 0 && nextX < N 
                    && nextY >= 0 && nextY < N){
                    map[nextX][nextY] = 1;
                }
            }
        }
        Array.printArray(map);
    }

    private static void move4WaysDirectly(int[][] map, int posX, int posY, int num, int N){
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        System.out.println("================ 상하좌우 - 한방향부터 채우기");
        for(int d=1; d<=num; d++){
            for(int i=0; i<4; i++){
                int nextX = posX + dx[i]*d;
                int nextY = posY + dy[i]*d;

                while(nextX >= 0 && nextX < N  && nextY >= 0 && nextY < N){
                    map[nextX][nextY] = 1;
                    nextX = nextX + dx[i]*d;
                    nextY = nextY + dy[i]*d;
                }
                Array.printArray(map);
            }
        }
    }

    private static void move8WaysDirectly(int[][] map, int posX, int posY, int num, int N){
        int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1};

        System.out.println("================ 상하좌우 - 한방향부터 채우기 (8방향)");
        for(int d=1; d<=num; d++){
            for(int i=0; i<8; i++){
                int nextX = posX + dx[i]*d;
                int nextY = posY + dy[i]*d;

                while(nextX >= 0 && nextX < N  && nextY >= 0 && nextY < N){
                    map[nextX][nextY] = 1;
                    nextX = nextX + dx[i]*d;
                    nextY = nextY + dy[i]*d;
                }
                Array.printArray(map);
            }
        }
    }
}

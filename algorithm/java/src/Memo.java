import grammer.array.Array;

import java.lang.reflect.Member;

public class Memo {

    public static void main(String[] args) {

        int x = 0;
        int y = 0;
        int depth = 6;
        int[][] map = new int[4][6];

//        way4(x, y, depth, map);
        way8(x, y, depth, map);
        Array.printArray(map);
    }

    public static void way4(int x, int y, int depth, int[][] map){
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        map[x][y] = 1;
        int index = 2;

        for(int d=1; d<=depth; d++){
            for(int i=0; i<4; i++){
                int nextX = x + dx[i]*d;
                int nextY = y + dy[i]*d;

//                if(nextX >= 0 && nextX < map.length && nextY >= 0 && nextY < map[0].length)
//                    map[nextX][nextY] = index++;

                // 한방향부터 탐색
                while (nextX >= 0 && nextX < map.length && nextY >= 0 && nextY < map[0].length){
                    map[nextX][nextY] = index++;
                    nextX = nextX + dx[i]*d;
                    nextY = nextY + dy[i]*d;
                }
            }
        }
    }

    public static void way8(int x, int y, int depth, int[][] map) {
        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

        map[x][y] = 1;
        int index = 2;

        for(int d=1; d<depth; d++){
            for(int i=0; i<8; i++){
                int nextX = x + dx[i]*d;
                int nextY = y + dy[i]*d;

                if(nextX >= 0 && nextX < map.length && nextY >= 0 && nextY < map[0].length)
                    map[nextX][nextY] = index++;
            }
        }
    }

}

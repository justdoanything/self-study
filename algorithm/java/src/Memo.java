public class Memo {
  
  public static void main(String[] args) {

    int x = 0;
    int y = 0;
    int depth = 7;
    
    int[][] map = new int[3][2];
    move4Ways(map, x, y, depth);

    map = new int[5][4];
    move8Ways(map, x, y, depth);
  }

  // 행렬 탐색
  private static void move4Ways(int[][] map, int x, int y, int depth){
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};

    map[x][y] = 1;
    for(int d=1; d<=depth; d++){
      for(int i=0; i<4; i++){
        int nextX = x + dx[i]*d;
        int nextY = y + dy[i]*d;

        if(nextX >=0 && nextX < map.length && nextY >= 0 && nextY < map[0].length){
          map[nextX][nextY] = 1;
        }
      }
    }
    printArray(map);
  }

  private static void move8Ways(int[][] map, int x, int y, int depth) {
    int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    map[x][y] = 1;
    for(int d=1; d<=depth; d++){
      for(int i=0; i<8; i++){
        int nextX = x + dx[i]*d;
        int nextY = y + dy[i]*d;

        if(nextX >=0 && nextX < map.length && nextY >= 0 && nextY < map[0].length){
          map[nextX][nextY] = 1;
        }
      }
    }
    printArray(map);
  }
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

}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Memo {
  
  public static void main(String[] args) {

    int x = 0;
    int y = 0;
    int depth = 7;
    
    int[][] map = new int[3][2];
    move4Ways(map, x, y, depth);

    map = new int[5][4];
    move8Ways(map, x, y, depth);

    List<String> stringList = new ArrayList<>();
    stringList.add("b");
    stringList.add("d");
    stringList.add("a");
    stringList.add("y");
    stringList.add("l");

    stringList.sort((str1, str2) -> {
      return  0;
    });

    
    
    System.out.println(stringList.stream().sorted((o1, o2) -> o1.compareTo(o2) ).toList());
    stringList.sort((str1, str2) -> str1.compareTo(str2));
    System.err.println(stringList);

    stringList.sort(Comparator.naturalOrder());
    System.out.println(stringList);

    stringList.sort(Comparator.reverseOrder());
    System.out.println(stringList);

    int[] intArray = {5, 6, 2, 7, 8, 11, 45, 75, 90};
    for(int i=0; i<intArray.length; i++)
      System.out.println(intArray[i]);
    
    
    System.out.println("=====================nPr");
    nPr(intArray, 9, 2, 0);
    System.out.println(count);
    System.out.println(permutation(9,2));
    System.out.println(combination(9,2));

    System.out.println(Arrays.binarySearch(intArray, 5));
    
    Arrays.sort(intArray);
    Arrays.stream(intArray).forEach(System.out::print);
    System.out.println();
    System.err.println(binarySearch(intArray, 7));
    
    String temp = "z";

    String b1 = Integer.toBinaryString(8);
    System.out.println(String.format("%08d", Integer.parseInt(b1)));
    
    System.out.printf("%08d", Integer.parseInt(b1));

    String str1 = "A";
    String str2 = "C";
    System.out.println();
    System.out.println(str1.compareTo(str2));
  }

  private static int binarySearch(int[] array, int target){

    int high = array.length-1;
    int low = 0;
    int mid;
    while(high >= low) {
      mid = (high + low) / 2;

      if(target == array[mid]){
        return mid;
      }
      else if(target > array[mid]){
        // target이 오른쪽에 있다.
        low = mid+1;
      }else {
        high = mid-1;
      }
    }
    return -1;
  }

  static int count = 0;
  private static void nPr(int[] array, int n, int r, int depth){
    if(depth == r){
      for(int i=0; i<r; i++){
        System.out.print(array[i] + " ");
      }
      System.out.println();
      count++;
    }

    for(int i=depth; i<n; i++){
      swap(array, i, depth);
      nPr(array, n, r, depth+1);
      swap(array, i, depth);
    }
  }

  private static void swap(int[] array, int i, int depth){
    int temp = array[i];
    array[i] = array[depth];
    array[depth] = temp;
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
  
  public static void printArray(int[][] array) {
    for(int i=0; i<array.length; i++){
        for(int j=0; j<array[0].length; j++){
            System.out.print(array[i][j] + "\t");
        }
        System.out.println();
    }
    System.out.println();
  }

  // 팩토리얼(!)
private static int factorial(int n) {
	if( n == 1)
  	return 1;
  else
    return n * factorial(n - 1);
}
// 순열(nPr)
private static int permutation(int n, int r) {
  if(r == 1)
      return n;
  else
      return n * permutation(n - 1, r - 1);
}
// 조합(nCr)
private static int combination(int n, int r) {
  return permutation(n, r) / factorial(r);
}

}

package solution.array;

public class A02 {
    // 2차원 행렬이 있을 때, 원본 배열이랑 원본 배열을 왼쪽으로 180도 회전한 배열을 합치고 각 인덱스의 합을 10으로 나눈 배열을 생성하고 그 배열을 지그재그(왼쪽->오른쪽, 오른쪽->왼쪽)로 읽으면서 값들을 이어붙인 String을 반환해라.
    public static void main(String[] agrs) {
      int[][] data = {
          {2,7,11,9}
          ,{13,8,12,3}
          ,{31,18,5,6}
          ,{7,17,14,7}
      };
      System.out.println(solution(data));
    }
    
    private static String solution(int[][] data) {
      int[][] temp = new int[data.length][data.length];
      String result = "";

      int n = data.length;
      for(int i=0; i<n; i++){
          for(int j=0; j<n; j++){
              temp[i][j] = (data[i][j] + data[n-1-i][n-1-j]) % 10;
          }
      }
      
      for(int i=0; i<n; i++){
        if(i%2 == 0){
          for(int j=0; j<n; j++){
            result += temp[i][j];
          }
        }else {
          for(int j=n-1; j>=0; j--){
            result += temp[i][j];
          }
        }
      }
      
      return result;
    }
}

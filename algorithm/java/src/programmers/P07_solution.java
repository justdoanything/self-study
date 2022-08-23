package programmers;

import java.util.ArrayList;
import java.util.List;

public class P07_solution {

  static int up = 0;
  static int down = 0;
  static int count = 0;

  static List<String> list = new ArrayList<>();
  public static void main(String[] args){
    
    System.out.println(solution(new int[]{1, 2, 5, 5, 5, 9}, 2, 1));
    count = 0;
    System.out.println(solution(new int[]{1, 2, 3, 3, 3, 5}, 2, 3));
  }
  
  public static int solution(int[] arr, int A, int B) {
    int answer = 0;

    nPr(arr, arr.length, arr.length, 0, A, B);

    answer = count;
    return answer;
  }

  public static void nPr(int[] arr, int n, int r, int depth, int A, int B){
      if(depth == r){
        // 순열 조합 완성
        if(isExist(arr)){
          return;
        }
        
        up = 0;
        down = 0;
        for(int i=0; i<arr.length-1;i++){
          if(arr[i] == arr[i+1])
            continue;
          if(arr[i] < arr[i+1])
            up++;
          else if(arr[i] > arr[i+1])
            down++;
        }
    
        if(up == A && down == B)
          count++;
    }

    for(int i=depth; i<n; i++){
        swap(arr, depth, i);
        nPr(arr, n, r, depth+1, A, B);
        swap(arr, depth, i);
    }
  }

  private static void swap(int[] num, int depth, int i){
    int temp = num[depth];
    num[depth] = num[i];
    num[i] = temp;
  }

  public static boolean isExist(int[] arr){
    String temp = "";
    for(int i=0; i<arr.length; i++){
      temp += arr[i];
    }
    if(list.contains(temp))
      return true;
    else{
      list.add(temp);
      return false;
    }
    
  }
}
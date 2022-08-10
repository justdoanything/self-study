package src.programmers;

public class Main {
    
    static int result = 0;
    private static int count = 0;
    public static void main(String[] args) throws Exception {
        int[] test = {1, 2, 3, 3, 3, 5};
        System.out.println(solution(test, 2, 3));

    }

    private static int solution(int[] arr, int A, int B) {
        int answer = 0;
        
        permutation(arr, arr.length, arr.length, 0, A, B);
        
        answer = result/arr.length;
        return answer;
    }

    public static boolean isAnswer(int[] arr, int A, int B){
        int inc = 0;
        int dec = 0;
        for(int i=0; i<arr.length-1; i++){
            int x = arr[i];
            int y = arr[i+1];
            if(arr[i] > arr[i+1])
                inc++;
            else if(arr[i] < arr[i+1])
                dec++;                
        }

        if(inc == A && dec == B)
            return true;
        else
            return false;
    }

    private static void permutation(int[] num, int n, int r, int depth, int A, int B) {
        if(depth == r){
            int[] temp = new int[num.length];
            for(int i=0; i<r; i++){
                temp[i] = num[i];
            }
            if(isAnswer(temp, A, B))
                result++;
            count++;
            return;
        }

        for(int i=depth; i<n; i++){
            swap(num, depth, i);
            permutation(num, n, r, depth+1, A, B);
            swap(num, depth, i);
        }
    }

    private static void swap(int[] num, int depth, int i){
        int temp = num[depth];
        num[depth] = num[i];
        num[i] = temp;
    }
}

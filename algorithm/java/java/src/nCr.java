import java.util.LinkedList;

public class nCr {
    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4};
        int n = num.length;
        int r = 2; 
        int start = 0;

        // 조합
        int[] temp = new int[2];
        combination(num, temp, n, r, 0, 0);
        
        System.out.println("=======================================================");

        // 조합 (visit)
        boolean[] visit = new boolean[num.length];
        combinationVisit(num, n, r, start, visit);

        System.out.println("=======================================================");

        // 중복 조합
        // combinationRepeat(num, n, r, list);
    }

    private static void combination(int[] num, int[] temp, int n, int r, int idx, int target) {
        if(r == 0){
            for(int i : temp) {
                System.out.print(i + " ");
            }
            System.out.println();
            return;
        }

        if(target == n) return;
        temp[idx] = num[target];
        combination(num, temp, n, r-1, idx+1, target+1); // 선택함
        combination(num, temp, n, r, idx, target+1);     // 선택 안함
        
    }
    
    private static void combinationVisit(int[] num, int n, int r, int start, boolean[] visit){
        if(r == 0) {
            for(int i=0; i<num.length; i++){
                if(visit[i])
                    System.out.print(num[i] + " ");
            }
            System.out.println();
            return;
        }
        
        for(int i=start; i<num.length; i++){
            visit[i] = true;
            combinationVisit(num, n, r-1, i+1, visit);
            visit[i] = false;
        }
        
    }

    private static void combinationRepeat(int[] num, int n, int r, LinkedList<Integer> list){
        
    }

    private static void swap(int[] num, int depth, int i){
        int temp = num[depth];
        num[depth] = num[i];
        num[i] = temp;
    }
}

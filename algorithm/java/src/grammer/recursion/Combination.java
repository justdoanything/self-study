package grammer.recursion;

public class Combination {

    private static int count = 0;

    public static void main(String[] args) {
        int[] num = {2, 2, 3, 3};
        int n = num.length;
        int r = 4; 
        int start = 0;

        int idx = 0;
        int target = 0;

        // 조합
        int[] temp = new int[num.length];
        count = 0;
        combination(num, temp, n, r, idx, target);
        System.out.println("==> " + count);
        
        System.out.println("=======================================================");

        // 조합 (visit)
        boolean[] visit = new boolean[num.length];
        count = 0;
        combinationVisit(num, n, r, start, visit);
        System.out.println("==> " + count);

        System.out.println("=======================================================");

        // 중복 조합
        temp = new int[num.length];
        count = 0;
        combinationRepeat(num, temp, n, r, idx, target);
        System.out.println("==> " + count);
    }

    private static void combination(int[] num, int[] temp, int n, int r, int idx, int target) {
        if(r == 0){
            for(int i : temp) {
                System.out.print(i + " ");
            }
            System.out.println();
            count++;
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
            count++;
            return;
        }
        
        for(int i=start; i<num.length; i++){
            visit[i] = true;
            combinationVisit(num, n, r-1, i+1, visit);
            visit[i] = false;
        }
        
    }

    private static void combinationRepeat(int[] num, int[] temp, int n, int r, int idx, int target){
        if(r == 0){
            for(int i : temp){
                System.out.print(i + " ");
            }
            System.out.println();
            count++;
            return;
        }

        if(target == n) return;
        temp[idx] = num[target];
        combinationRepeat(num, temp, n, r-1, idx+1, target);
        combinationRepeat(num, temp, n, r, idx, target+1);
    }
}

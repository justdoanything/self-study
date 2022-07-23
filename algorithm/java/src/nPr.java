package src;

import java.util.LinkedList;

// 순열과 조합
public class nPr {

    private static int count = 0;
    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 5};
        int n = num.length;
        int r = 2; 
        int depth = 0;

        // 순열
        count = 0;
        permutation(num, n, r, depth);
        System.out.println("==> " + count);
        System.out.println("=======================================================");

        // 순열 (visit)
        boolean[] visit = new boolean[num.length];
        LinkedList<Integer> list = new LinkedList<>();
        count = 0;
        permutationVisit(num, n, r, list, visit);
        System.out.println("==> " + count);

        System.out.println("=======================================================");

        // 중복순열
        list = new LinkedList<>();
        count = 0;
        permutationRepeat(num, n, r, list);
        System.out.println("==> " + count);
    }

    private static void permutation(int[] num, int n, int r, int depth) {
        if(depth == r){
            for(int i=0; i<r; i++){
                System.out.print(num[i] + " ");
            }
            System.out.println();
            count++;
            return;
        }

        for(int i=depth; i<n; i++){
            swap(num, depth, i);
            permutation(num, n, r, depth+1);
            swap(num, depth, i);
        }

        // for(int i=depth; i<n; i++){
        //     System.out.println("[함수 호출 전] swap 전 " + depth + " " + i + " " + Arrays.toString(num));
        //     swap(num, depth, i);
        //     System.out.println("[함수 호출 전] swap 후 " + depth + " " + i + " " + Arrays.toString(num));
        //     permutation(num, n, r, depth+1);
        //     System.out.println("[함수 호출 후] swap 전 " + depth + " " + i + " " + Arrays.toString(num));
        //     swap(num, depth, i);
        //     System.out.println("[함수 호출 후] swap 후 " + depth + " " + i + " " + Arrays.toString(num));
        // }
    }
    
    private static void permutationVisit(int[] num, int n, int r, LinkedList<Integer> list, boolean[] visit){
        if(list.size() == r){
            for(Integer i : list) {
                System.out.print(i + " ");
            }
            System.err.println();
            count++;
            return;
        }

        for(int i=0; i<n; i++){
            if(!visit[i]){
                visit[i] = true;
                list.add(num[i]);
                permutationVisit(num, n, r, list, visit);
                visit[i] = false;
                list.removeLast();
            }
        }
    }

    private static void permutationRepeat(int[] num, int n, int r, LinkedList<Integer> list){
        if(list.size() == r){
            for(int i: list) {
                System.out.print(i + " ");
            }
            System.err.println();
            count++;
            return;
        }

        for(int i=0; i<n; i++){
            list.add(num[i]);
            permutationRepeat(num, n, r, list);
            list.removeLast();
        }
    }

    private static void swap(int[] num, int depth, int i){
        int temp = num[depth];
        num[depth] = num[i];
        num[i] = temp;
    }

}
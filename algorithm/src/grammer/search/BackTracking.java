package grammer.search;

import java.util.Arrays;

public class BackTracking {
    
    // N Queen 문제
    // 체스판에서 Queen은 같은 행과 열에서 1개만 있을 수 있고 대각선에 인접(1칸)할 수 없다.
    // 위 경우가 부합되는 경우의 수를 구하시오
    
    static int col[];
    static int n;
    static int ans;
    static int count;
    
    public static void main(String[] args) {
        n = 4;

        for(int i=1; i<=n; i++){
            col = new int[n*n];
            col[1] = i;

            System.out.println(i + " : " + Arrays.toString(col));

            dfs(2);
        }

        System.out.println("정답 : " + ans);
        System.out.println("탐색 횟수 : " + count);
    }

    public static void dfs(int row){
        //현재 열이 체스판을 넘어섰다면
        if(row > n){
            ++ans;
        } else{
            for(int i=1; i<=n; i++){
                count++;
                // 현재 위치하고 있는 노드의 좌표를 저장 (row : 열, i: 행)
                col[row] = i;

                // 유망한 노드 검초
                if(isPossible(row)){
                    // 서브 트리로 이동 (해당 노드의 화위 노드)
                    dfs(row + 1);
                } else { 
                    // back tracking 수행, 해당 노드는 가지치기가 된다.
                    col[row] = 0;
                }
            }
        }
        System.out.println(row + " : " + Arrays.toString(col));
    }

    public static boolean isPossible(int c) {
        //이전 열들을 탐색하면서 유망한 노드인지 확인
        for(int i=1; i<c; i++){
            // 상위 노드에서 같은 행에 퀸이 있는지 확인
            if(col[i] == col[c]){
                return false;
            }
            // 대각선 검사 : 상위 노드의 퀸과 현재 노드의 퀸이 가로/세로 거리가 같은지 검사
            if(Math.abs(col[i] - col[c]) == Math.abs(i - c)){
                return false;
            }
        }
        return true;
    }
}

package programmers.lv1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class lv1_크레인인형뽑기게임_2019KakaoWinterInternship {
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0,0,0,0,0},{0,0,1,0,3},{0,2,5,0,1},{4,2,4,4,2},{3,5,1,3,1}},new int[]{1,5,3,5,1,2,1,4}));
    }

    public static int solution(int[][] board, int[] moves) {
        int answer = 0;
        Stack<Integer> basket = new Stack<>();
        for(int pick: moves){
            pick -= 1;
            for(int row=0; row < board.length; row++){
                if(board[row][pick] != 0){
                    if(basket.isEmpty()){
                        basket.add(board[row][pick]);
                        board[row][pick] = 0;
                        break;
                    }
                    int before = basket.peek();
                    int now = board[row][pick];

                    if(before == now){
                        basket.pop();
                        answer += 2;
                    }else{
                        basket.add(now);
                    }
                    board[row][pick] = 0;
                    break;
                }
            }
        }
        System.out.println(basket);
        return answer;
    }
}

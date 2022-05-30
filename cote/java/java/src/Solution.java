public class Solution {
    public static void main(String[] args) throws Exception {
        
        int row = 4;
        int col = 3;
        
        solution(row, col);
    }

    private static void solution(int row, int col) {
        int[][] array = new int[row][col];
        int index = 0;
        // 0,0 시작
        // 왼쪽 -> 오른쪽
        index = 1;
        for(int i=0; i<row; i++){           
            for(int j=0; j<col; j++){       
                array[i][j] = index++;
            }
        }
        printArray(array);
        
        // 위 -> 아래
        index = 1;
        for(int i=0; i<col; i++){
            for(int j=0; j<row; j++){
                array[j][i] = index++;
            }
        }
        printArray(array);

        // 왼쪽 -> 오른쪽 (지그재그)
        index = 1;
        for(int i=0; i<row; i++){      
            if(i % 2 == 0){
                for(int j=0; j<col; j++){       
                    array[i][j] = index++;
                }
            } else {
                for(int j=col-1; j>=0; j--){       
                    array[i][j] = index++;
                }
            }  
        }
        printArray(array);
        
        // 위 -> 아래 (지그재그)
        index = 1;
        for(int i=0; i<col; i++){
            if(i % 2 == 0){
                for(int j=0; j<row; j++){
                    array[j][i] = index++;
                }
            }
            else{
                for(int j=row-1; j>=0; j--){
                    array[j][i] = index++;
                }
            }
        }
        printArray(array);

        // n,n 시작
        // 오른쪽 -> 왼쪽
        

        // 오른쪽 -> 왼쪽 (지그재그)
        // 아래 -> 위
        // 아래 -> 위 (지그재그)
    }
    
    private static void printArray(int[][] array) {
        for(int i=0; i<array.length; i++){
            for(int j=0; j<array[0].length; j++){
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}

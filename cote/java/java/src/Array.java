public class Array {
    public static void main(String[] args) throws Exception {
        
        int row = 5;
        int col = 4;
        
        solution(row, col);
    }

    private static void solution(int row, int col) {
        int[][] array = new int[row][col];
        int index = 0;
        // 0,0 시작
        
        // 왼쪽 -> 오른쪽
        System.out.println("################ 0,0 시작 - 왼쪽->아래");
        index = 1;
        for(int i=0; i<row; i++){           
            for(int j=0; j<col; j++){       
                array[i][j] = index++;
            }
        }
        printArray(array);
        
        // 위 -> 아래
        System.out.println("################ 0,0 시작 - 위->아래");
        index = 1;
        for(int i=0; i<col; i++){           // **point
            for(int j=0; j<row; j++){       // **point
                array[j][i] = index++;
            }
        }
        printArray(array);

        // 왼쪽 -> 오른쪽 (지그재그)
        index = 1;
        System.out.println("################ 0,0 시작 - 왼쪽->오른쪽(지그재그)");
        for(int i=0; i<row; i++){      
            if(i % 2 == 0){         // **point
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
        System.out.println("################ 0,0 시작 - 위->아래(지그재그)");
        for(int i=0; i<col; i++){
            if(i % 2 == 0){         // **point
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
        index = 1;
        System.out.println("################ n,n 시작 - 오른쪽->왼쪽");
        for(int i=row-1; i>=0; i--){
            for(int j=col-1; j>=0; j--){
                array[i][j] = index++;
            }
        }
        printArray(array);

        // 아래 -> 위
        index = 1;
        System.out.println("################ n,n 시작 - 아래->위");
        for(int i=col-1; i>=0; i--){        // **point
            for(int j=row-1; j>=0; j--){    // **point
                array[j][i] = index++;
            }
        }
        printArray(array);

        // 오른쪽 -> 왼쪽 (지그재그)
        index = 1;
        System.out.println("################ n,n 시작 - 오른쪽->왼쪽(지그재그)");
        for(int i=row-1; i>=0; i--){
            if(row % 2 == 0){   // **point
                if(i % 2 == 0){
                    for(int j=0; j<col; j++){
                        array[i][j] = index++;
                    }
                }else {
                    for(int j=col-1; j>=0; j--){
                        array[i][j] = index++;
                    }
                }
            } else{ // **point
                if(i % 2 == 0){
                    for(int j=col-1; j>=0; j--){
                        array[i][j] = index++;
                    }
                }else {
                    for(int j=0; j<col; j++){
                        array[i][j] = index++;
                    }
                }
            }
        }
        printArray(array);

        // 아래 -> 위 (지그재그)
        index = 1;
        System.out.println("################ n,n 시작 - 아래->위(지그재그)");
        for(int i=col-1; i>=0; i--){
            if(col % 2 == 0){   // **point
                if(i % 2 == 0){
                    for(int j=0; j<row; j++){
                        array[j][i] = index++;
                    }
                }else {
                    for(int j=row-1; j>=0; j--){
                        array[j][i] = index++;
                    }
                }
            } else{ // **point
                if(i % 2 == 0){
                    for(int j=row-1; j>=0; j--){
                        array[j][i] = index++;
                    }
                }else {
                    for(int j=0; j<row; j++){
                        array[j][i] = index++;
                    }
                }
            }
        }
        printArray(array);

        // 대각선으로 넣기
        // 값이 들어가는 위치를 보면 row + col을 더한 값은 0부터 4로 일정하다.
        index = 1;
        System.out.println("################ 0, 0 시작 - 대각선 출력");
        
        // 전체 탐색을 위한 변수 c , 범위는 row+col
        for(int c=0; c<= row+col; c++){
            for(int i=0; i<row; i++){
                for(int j=0; j<col; j++){
                    // i와 j의 합이 c와 같은 곳에서만 출력
                    if(i+j == c)
                        array[i][j] = index++;
                }
            }
        }
        printArray(array);

        index = 1;
        System.out.println("################ 0, 0 시작 - 대각선 출력(반대)");
        
        // 전체 탐색을 위한 변수 c , 범위는 row+col
        for(int c=0; c<= row+col; c++){
            for(int i=0; i<col; i++){
                for(int j=0; j<row; j++){
                    // i와 j의 합이 c와 같은 곳에서만 출력
                    if(i+j == c)
                        array[j][i] = index++;
                }
            }
        }
        printArray(array);
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
}

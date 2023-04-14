package programmers.edu;

class P02_empty {
    int[] dy = /**/ {-1, 1, 0, 0}; 
    int[] dx = /**/ {0, 0, -1, 1};
 
    int N ,M; 
    void bomb(int y , int x, int k, char[][] MAP) {
        MAP[y][x] = '%'; 
        for(int t = 0 ; t< 4; t ++) { 
            for(int mul = 1; mul <= k; mul ++) {
                int ny = y + dy[t] * mul ;
                int nx = x + dx[t] * mul ; 
                if(ny < 0 || nx < 0 || ny >= N || nx >= M)
                    break;
                
                if(MAP[ny][nx] == '#') 
                    break;/**/
 
                if(MAP[ny][nx] == '@')
                    break;/**/

                MAP[ny][nx] = '%'; 
            }
        }
    }
    public String[] solution(int n, int m, int k, String[] arr) {
        N = n ; 
        M = m ; 
        char[][] MAP = new char[n][m]; 
        for(int y = 0 ; y < n; y ++) { 
            MAP[y] = arr[y].toCharArray();
        }
        for(int y = 0; y < n; y ++) { 
            for(int x = 0 ; x < m; x ++) { 
                if(MAP[y][x] == '@') { 
                    bomb(y,x,k,MAP); 
                }
            }
        }
        String[] answer = new String[n];
        for(int y = 0 ; y < n; y ++) { 
            answer[y] = new String(MAP[y]);
        }
        return answer;

    }
}
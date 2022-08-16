package programmers;

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

/*
N x M 의 격자점 공간에 K 화력의 폭탄들이 설치 되어있습니다. N은 세로 크기이며, M은 가로 크기입니다. 여러 개의 폭탄들이 동시에 터진다고 했을 때, 터지고 난 후의 모양을 알아내는 프로그램을 제작하려 합니다.

폭탄은 모두 동시에 터지며, 폭탄이 있는 곳과 상, 하, 좌, 우로 각각 K 칸으로 퍼집니다. 벽이 있는 곳은 폭발이 차단됩니다.

아래 그림은 화력(K)이 1 일때, 예시 이미지입니다. 벽이 없는 지역만 폭탄이 터집니다.
image.png

아래는 화력(K)이 2 인 경우를 나타낸 이미지입니다. 동시에 터지기 때문에 오른쪽 그림과 같은 폭발이 발생합니다.
image.png

주어진 초기 코드는 폭발 전 상태가 주어지면, 폭발 후 상태를 리턴 해주는 함수입니다. 빈칸을 채워서 해당 코드를 완성시켜주세요.

[문자 의미]

문자	의미
#	벽
@	폭탄
%	폭발 발생
_	빈칸 

테스트 1
입력값 〉	3, 5, 2, ["_#_\#@", "_#_\#@", "@___#"]
기댓값 〉	["%#_#%", "%#_#%", "%%%_#"]
실행 결과 〉	테스트를 통과하였습니다.
*/
## 소소한 문법
- `Arrays.stream( ... )` : Array 👉 Stream
- `Stream.of ( ... )` : Array 👉 Stream
- `mapToInt` : Stream 👉 IntStream
- `boxed()`
  ```java
  List<Integer> list2 = Arrays.stream(array)
                                .boxed()
                                .collect(Collectors.toList());
  ```

- IntStream의 `max()`, `min()`, `sum()`, `average()`
  ```java
  private intStream() {
    transactions.stream()
						.mapToInt(Transaction::getValue)
						.min()
						.ifPresent(System.out::println);
	}
	```
	
- `sort()` and `sorted()`
	```java
	private listSort() {
		inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
		inventory.sort(Comparator.comparing(Apple::getWeight)
													.reversed()
													.thenComparing(Apple::getCountry));
		stringList.sort((String str1, String str2) -> {
			if(str2.contains("Bad") || str2.contains("Awful"))
				return 1;
			else if(!str1.contains("Bad") && !str1.contains("Awful") && !str2.contains("Bad") && !str2.contains("Awful"))
				return 0;
			else
				return -1;
		});
	}
	```
	```java
	private streamSorted() {
		// 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
		System.out.println("2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.");
		transactions.stream()
						.filter(t -> t.getYear() == 2011)
						.sorted(Comparator.comparing(Transaction::getValue))
						.forEach(System.out::println);
		
		// 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
		System.out.println("\n케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.");
		transactions.stream()
						.map(Transaction::getTrader)
						.filter(t -> t.getCity().equals("Cambridge"))
						.distinct()
						.sorted(Comparator.comparing(Trader::getName))
						.forEach(System.out::println);

		// 모든 거래자의 이름을 알파뱃순으로 정렬해서 반환하시오.
		System.out.println("\n모든 거래자의 이름을 알파뱃순으로 정렬해서 반환하시오.");
		transactions.stream()
						.map(t -> t.getTrader().getName())
						.sorted(Comparator.reverseOrder())
						.distinct()
						.forEach(System.out::println);
	}
	```
## 소소한 공식
- ### 메모
  - 지그재그(ㄹ자) 탐색은 if(i%2==0) 분기분 사용
  - 영어 좌표 👉 숫자 좌표
    ```java
    String posEng = "d4";
    int posEngX = posEng.charAt(0)-'a';
    int posEngY = posEng.charAt(1)-'0';
    ```
  - 대각선 검사 (N Queen 문제)
    ```java
    boolean isPossible(int c) {
        //이전 열들을 탐색하면서 유망한 노드인지 확인
        for(int i=1; i<c; i++)P{
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
    ```

---

- ### 정방향 탐색
```java
index = 1;
System.out.println("################ n,n 시작 - 오른쪽->왼쪽");
for(int i=row-1; i>=0; i--){
    for(int j=col-1; j>=0; j--){
        array[i][j] = index++;
    }
}
```
- ### ㄹ자 탐색
```java
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
```
- ### 대각선 탐색
```java
// 전체 탐색을 위한 변수 c , 범위는 row+col
index = 1;
System.out.println("################ n, n 시작 - 대각선 출력");
for(int c=row+col; c>=0; c--){
    for(int i=col-1; i>=0; i--){
        for(int j=row-1; j>=0; j--){
            // i와 j의 합이 c와 같은 곳에서만 출력
            if(i+j == c)
                array[i][j] = index++;
        }
    }
}
```

---

- ### 4방향 탐색
```java
// 4방향 탐색
private static void move4Ways(int[][] map, int posX, int posY, int num, int N){
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    System.out.println("================ 상하좌우");
    for(int d=1; d<=num; d++){
        for(int i=0; i<4; i++){
            int nextX = posX + dx[i]*d;
            int nextY = posY + dy[i]*d;
            if(nextX >= 0 && nextX < N 
                && nextY >= 0 && nextY < N){
                map[nextX][nextY] = 1;
            }
        }
    }
    Array.printArray(map);
}
```
- ### 8방향 탐색
```java
// 8방향 탐색
private static void move8Ways(int[][] map, int posX, int posY, int num, int N){
    int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1};
    int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1};
    System.out.println("================ 상하좌우대각선");
    for(int d=1; d<=num; d++){
        for(int i=0; i<8; i++){
            int nextX = posX + dx[i]*d;
            int nextY = posY + dy[i]*d;
            if(nextX >= 0 && nextX < N 
                && nextY >= 0 && nextY < N){
                map[nextX][nextY] = 1;
            }
        }
    }
    Array.printArray(map);
}
```

---

- ### 최대공약수(gcd) / 최대공배수(lcm)
```java
// 최대공약수(gcd)
private static int gcd(int a, int b){
  while(b != 0){
    int r = a%b;
    a = b;
    b = r;
  }
  return a;
}
// 최소공배수(lcm)
public static int lcm(int a, int b){
  return a*b/gcd(a,b);
}
```

---

- ### 팩토리얼(!) / 순열(nPr) / 조합(nCr)
```java
// 팩토리얼(!)
private static int factorial(int n) {
	if( n == 1)
  	return 1;
  else
    return n * factorial(n - 1);
}
// 순열(nPr)
private static int permutation(int n, int r) {
  if(r == 1)
      return n;
  else
      return n * permutation(n - 1, r - 1);
}
// 조합(nCr)
private static int combination(int n, int r) {
  return permutation(n, r) / factorial(r);
}
```

---

- ### BFS, DFS
```java
public static void bfs(int[][] graph, int start, boolean[] visited){
	Queue<Integer> queue = new LinkedList<>();
	queue.add(start);
	visited[start] = true;
	while(!queue.isEmpty()){
		int v = queue.poll();
		System.out.print(v + " ");
		for(int i : graph[v]){
			if(visited[i] == false){
				queue.add(i);
				visited[i] = true;
			}
		}
	}
}
public static void bfs(int[][] graph, int start, boolean[] visited, int n){
	Queue<Integer> queue = new LinkedList<>();
	queue.add(start);
	visited[start] = true;
	while(!queue.isEmpty()){
		int v = queue.poll();
		System.out.print(v + " ");
		for(int i=1; i<=n; i++){
			if(graph[v][i] == 1 && visited[i] == false){
				queue.add(i);
				visited[i] = true;
			}
		}
	}
}
```
```java
public static void dfs(int[][] graph, int v, boolean[] visited){
	visited[v] = true;
	System.out.print(v + " ");
	for(int i : graph[v]){
		if(visited[i] == false){
			dfs(graph, i, visited);
		}
	}
}
public static void dfs(int[][] graph, int v, boolean[] visited, int n){
	visited[v] = true;
	System.out.print(v + " ");
	for(int i=1; i<=n; i++){
		if(graph[v][i] == 1 && visited[i] == false){
			dfs(graph, i, visited, n);
		}
	}
}
```
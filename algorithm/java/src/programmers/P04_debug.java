package programmers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Node  {
	int node; 
	int cost; 
	Node(int node, int cost) { 
		this.node = node; 
		this.cost = cost; 
	}
}
class P04_debug {

	public int solution(int start) {
		ArrayList<Node>[] adj = new ArrayList[6];
		for(int i = 1; i <= 5; i ++) adj[i] = new ArrayList<Node>(); 
		adj[1].add(new Node(2,3));
		adj[1].add(new Node(3,5));
		adj[2].add(new Node(3,4));
		adj[2].add(new Node(4,1));
		adj[3].add(new Node(4,3));
		adj[3].add(new Node(5,2));
		adj[4].add(new Node(5,7));
		boolean[] visited = new boolean[6];
		
		Queue<Node> qu = new LinkedList<>();
		visited[start] = true; 
		qu.add(new Node(start,0));
		
		while (!qu.isEmpty()) { 
			Node now = qu.poll(); 
			if(now.node == 5) { 
				return now.cost; 
			}
			
			for(Node next : adj[now.node])
			{
				if(visited[next.node]) continue; 
				visited[next.node]= true; 
				qu.add(new Node(next.node, now.cost + next.cost));
			}
		}
		return -1;
	}
}

/*
BFS 를 이용해서 start 번 정점에서 5 번 정점으로 가기 위한 최소 이동 횟수를 구하려 합니다. solution 함수는 아래 그래프의 "start" 번 정점에서 5번 정점까지 가는 최소 이동 횟수를 반환해야 합니다.

image.png

초기 코드는 원하는 결과가 나오지 않는 버그가 나오는 코드입니다. 초기 코드를 정확히 한 줄을 수정해서 최소 이동 횟수를 리턴할 수 있는 코드를 완성 시켜주세요. 

테스트 1
입력값 〉	1
기댓값 〉	2
실행 결과 〉	실행한 결괏값 7이 기댓값 2과 다릅니다.

*/
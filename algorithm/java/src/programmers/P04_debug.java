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

	public static void main(String[] args) {
		System.out.println(solution(1));
	}
	
	public static int solution(int start) {
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

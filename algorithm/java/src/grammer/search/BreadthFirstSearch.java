package src.grammer.search;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {
    public static void main(String[] args) {
        int[][] graph = {
            {}
            ,{2,7}
            ,{1,8}
            ,{4,5,7}
            ,{4,6}
            ,{3,6,7}
            ,{4,5,8}
            ,{1,3,5,8}
            ,{2,6,7}
        };
        boolean[] visited = new boolean[graph.length];
        bfs(graph, 1, visited);

        System.out.println();
        
        int n = 8;
        int[][] vertex = {
            {1,2,}
            ,{1,7}
            ,{2,8}
            ,{7,8}
            ,{7,3}
            ,{7,5}
            ,{8,6}
            ,{5,6}
            ,{5,3}
            ,{3,4}
            ,{4,6}
        };
        visited = new boolean[n+1];
        graph = new int[n+1][n+1];
        
        for(int i=0; i<vertex.length; i++){
            graph[vertex[i][0]][vertex[i][1]] = 1;
            graph[vertex[i][1]][vertex[i][0]] = 1;
        }
        bfs(graph, 1, visited, n);
    }

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
}

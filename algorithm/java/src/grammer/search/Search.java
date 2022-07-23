package src.grammer.search;

public class Search {
    
    public static void main(String[] args){
        int[][] graph = {{},{2,7},{1,8},{4,5,7},{4,6},{3,6,7},{4,5,8},{1,3,5,8},{2,6,7}};
        boolean[] visited = new boolean[graph.length];
        System.out.print("bfs : ");
        BreadthFirstSearch.bfs(graph, 1, visited);
        
        graph = new int[][]{{},{2,7},{1,8},{4,5,7},{4,6},{3,6,7},{4,5,8},{1,3,5,8},{2,6,7}};
        visited = new boolean[graph.length];
        System.out.print("\ndfs : ");
        DepthFirstSearch.dfs(graph, 1, visited);
        
        int n = 8;
        int[][] vertex = {{1,2,},{1,7},{2,8},{7,8},{7,3},{7,5},{8,6},{5,6},{5,3},{3,4},{4,6}};
        visited = new boolean[n+1];
        graph = new int[n+1][n+1];
        for(int i=0; i<vertex.length; i++){
            graph[vertex[i][0]][vertex[i][1]] = 1;
            graph[vertex[i][1]][vertex[i][0]] = 1;
        }
        System.out.print("\nbfs : ");
        BreadthFirstSearch.bfs(graph, 1, visited, n);
        
        visited = new boolean[n+1];
        graph = new int[n+1][n+1];
        for(int i=0; i<vertex.length; i++){
            graph[vertex[i][0]][vertex[i][1]] = 1;
            graph[vertex[i][1]][vertex[i][0]] = 1;
        }
        System.out.print("\ndfs : ");
        DepthFirstSearch.dfs(graph, 1, visited, n);
        
    }
}

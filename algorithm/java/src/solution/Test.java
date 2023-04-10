package solution;

import grammer.array.Array;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] agrs) {
        int start = 2;
        int[][] graph = {
                {}
                , {2, 7}
                , {1, 8}
                , {4, 5, 7}
                , {4, 6}
                , {3, 6, 7}
                , {4, 5, 8}
                , {1, 3, 5, 8}
                , {2, 6, 7}
        };
        boolean[] visited = new boolean[graph.length];
        dfs(graph, start, visited);
        System.out.println();

        visited = new boolean[graph.length];
        bfs(graph, 1, visited);
        System.out.println();

        int n = 8;
        int[][] vertex = {
                {1, 2}
                , {1, 7}
                , {2, 8}
                , {7, 8}
                , {7, 3}
                , {7, 5}
                , {8, 6}
                , {5, 6}
                , {5, 3}
                , {3, 4}
                , {4, 6}
        };
        visited = new boolean[n + 1];
        graph = new int[n + 1][n + 1];

        for (int i = 0; i < vertex.length; i++) {
            graph[vertex[i][0]][vertex[i][1]] = 1;
            graph[vertex[i][1]][vertex[i][0]] = 1;
        }
        dfs(graph, start, visited, n);
        System.out.println();

        visited = new boolean[graph.length];
        bfs(graph, 1, visited, n);
        System.out.println();
    }

    private static void dfs(int[][] graph, int start, boolean[] visited) {
        visited[start] = true;
        System.out.print(start + " ");

        for (int next : graph[start]) {
            if (visited[next] == false) {
                dfs(graph, next, visited);
            }
        }
    }

    private static void dfs(int[][] graph, int start, boolean[] visited, int n) {
        visited[start] = true;
        System.out.print(start + " ");

        for (int next = 1; next <= n; next++) {
            if (graph[start][next] == 1 && visited[next] == false) {
                dfs(graph, next, visited, n);
            }
        }
    }

    private static void bfs(int[][] graph, int start, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int now = queue.poll();
            System.out.print(now + " ");

            for (int next : graph[now]) {
                if (visited[next] == false) {
                    queue.add(next);
                    visited[next] = true;
                }
            }
        }
    }

    private static void bfs(int[][] graph, int start, boolean[] visited, int n) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int now = queue.poll();
            System.out.print(now + " ");

            for (int next = 1; next <= n; next++) {
                if (graph[now][next] == 1 && visited[next] == false) {
                    queue.add(next);
                    visited[next] = true;
                }
            }
        }
    }

    private static int BinarySearch(int arr[], int target) {
        int low = 0;
        int high = arr.length - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;

            if (arr[mid] == target)
                return mid;
            else if (arr[mid] > target)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }

    int BinarySearchRecursive(int arr[], int target, int low, int high) {
        if (low > high)
            return -1;

        int mid = (low + high) / 2;
        if (arr[mid] == target)
            return mid;
        else if (arr[mid] > target)
            return BinarySearchRecursive(arr, target, low, mid - 1);
        else
            return BinarySearchRecursive(arr, target, mid + 1, high);
    }
}


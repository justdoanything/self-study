package programmers.edu;

import java.util.Arrays;

//class Node implements Comparable<Node>{
//    String name;
//    Node(String name){
//        this.name = name;
//    }
//    @Override
//    public int compareTo(Node o) {
//        if(this.name.length() != o.name.length()) {
//            return /**/ this.name.length() > o.name.length() ? 1 : -1;
//
//        }
//        return /**/this.name.compareTo(o.name);
//    }
//}
//class P03_empty {
//
//    public String[] solution(int N, String[] names) {
//        Node[] arr = new Node[N];
//        for(int i = 0 ; i < N; i ++) {
//            arr[i] = new Node(names[i]);
//        }
//        Arrays.sort(arr);
//        String[] answer = new String[N];
//        for(int i = 0; i < N; i ++) {
//            answer[i] = arr[i].name;
//        }
//        return answer;
//    }
//}
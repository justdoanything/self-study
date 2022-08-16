package programmers;

import java.util.Arrays;

class Node implements Comparable<Node>{
    String name; 
    Node(String name){ 
        this.name = name;
    }
    @Override
    public int compareTo(Node o) {
        if(this.name.length() != o.name.length()) {
            return /**/ this.name.length() > o.name.length() ? 1 : -1;

        }
        return /**/this.name.compareTo(o.name);
    }
}
class P03_empty {

    public String[] solution(int N, String[] names) {
        Node[] arr = new Node[N];
        for(int i = 0 ; i < N; i ++) {
            arr[i] = new Node(names[i]);
        }
        Arrays.sort(arr); 
        String[] answer = new String[N];
        for(int i = 0; i < N; i ++) { 
            answer[i] = arr[i].name;
        }
        return answer; 
    }
}

/*
연락처 앱에 정렬 기능을 추가하려고 합니다. 문자열 개수 N이 주어지고 , N명의 이름이 names 배열에 주어집니다. 동명이인이 있을 수 있습니다. names를 아래와 같은 조건으로 정렬 후, 결과를 return 할 수 있도록 빈칸의 코드를 채워주세요

정렬 우선순위
1. 길이가 짧은 이름이 왼쪽으로 나열
2. 사전 순서가 앞선 이름이 왼쪽으로 나열


테스트 1
입력값 〉	5, ["aaleh", "amily", "acome", "john", "java"]
기댓값 〉	["java", "john", "aaleh", "acome", "amily"]
실행 결과 〉	테스트를 통과하였습니다.

*/
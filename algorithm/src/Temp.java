import grammer.array.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Temp {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.addAll(Arrays.asList(1,2,3,4,5));
        System.out.println("stack : " + stack);

        System.out.println(stack.isEmpty());
        System.out.println(stack.size());

        System.out.println(stack.add(6)); // 마지막에 요소 추가 (boolean)
        System.out.println(stack.push(7)); // 마지막에 요소 추가 (넣은 요소 반환)

        System.out.println(stack.peek()); // 마지막 요소 반환

        System.out.println(stack.pop()); // 마지막에 요소 추출

        System.out.println(stack.capacity()); // Stack의 용량으로 기본값은 0이고 초과하면 자동으로 더 큰 용량으로 복사된다.

        System.out.println("stack : " + stack);
        System.out.println(stack.search(2)); // 요소의 위치 (뒤에서 부터)
        System.out.println(stack.get(2)); // n번째 요소 반환 (앞에서 부터)

        Queue<Integer> queue = new LinkedList<>();
        queue.addAll(Arrays.asList(1,2,3,4,5));
        System.out.println("queue : " + queue);

        System.out.println(queue.isEmpty());
        System.out.println(queue.size()); // Queue 크기

        System.out.println(queue.add(6)); // 마지막에 요소 추가 (boolean)
        System.out.println(queue.offer(7)); // 마지막에 요소 추가 (boolean)

        System.out.println(queue.peek()); // 첫번째 요소 반환
        System.out.println(queue.element()); // 첫번째 요소 반환

        System.out.println(queue.poll()); // 첫번째 요소 추출
        System.out.println(queue.remove()); // 첫번째 인자 추출


    }
}

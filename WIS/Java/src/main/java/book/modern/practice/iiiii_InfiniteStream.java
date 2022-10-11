package book.modern.practice;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class iiiii_InfiniteStream {
    
    public static void main(String[] args) {
        Stream.iterate(0, n->n+2)
                .limit(10)
                .forEach(System.out::println);
        
        IntStream.iterate(0, n->n<100, n->n+4)
                    .limit(10)
                    .forEach(System.out::println);

        IntStream.iterate(0, n->n+4)
                    .takeWhile(n -> n<100)
                    .limit(10)
                    .forEach(System.out::println);

        Stream.generate(Math::random)
                    .limit(10)
                    .forEach(System.out::println);

        IntSupplier fibo = new IntSupplier() {
            private int previous = 1;
            private int current = 2;
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fibo).limit(15).forEach(System.out::println);
    }
}

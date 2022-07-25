package modern.practice;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import modern.Dish;

public class iiii_NumberStreamPractive {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("season", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmons", false, 450, Dish.Type.FISH)
        );

        // sum, max, min, average
        System.out.println(menu.stream()
                                    .mapToInt(Dish::getCalories)
                                    .sum());

        menu.stream()
                .mapToInt(Dish::getCalories)
                .max()
                .ifPresent(System.out::println);

        menu.stream()
                .mapToInt(Dish::getCalories)
                .average()
                .ifPresent(System.out::println);
        
        // OptionalInt
        OptionalInt oi = menu.stream()
                .mapToInt(Dish::getCalories)
                .map(c -> c * -1)
                .max();
        int max = oi.orElse(1);

        /*
         * 숫자 범위 지정(range, rangeClosed)
         */ 
        IntStream.range(0, 10) // 0 < x < 100
                    .filter(n -> n % 2 == 0)
                    .forEach(System.out::println);
        
        IntStream.rangeClosed(0, 10) // 0 <= x <= 100
                    .filter(n -> n % 2 == 0)
                    .forEach(System.out::println);

        int[] array = {1, 2, 3, 4};
        List<int[]> list = Arrays.asList(array);

        List<Integer> list2 = Arrays.stream(array)
                                        .boxed()
                                        .collect(Collectors.toList());

        
        /*
         * 피타고라스
         */ 
        Stream<int[]> pythagorean = IntStream.rangeClosed(1, 100)
                                                .boxed()
                                                .flatMap(a ->
                                                    IntStream.rangeClosed(a, 100)
                                                        .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                                        .mapToObj(b ->
                                                            new int[]{a, b, (int)Math.sqrt(a*a + b*b)}));

        Stream<double[]> pythagorean2 = IntStream.rangeClosed(1, 100)
                                                    .boxed()
                                                    .flatMap(a -> IntStream.rangeClosed(a, 100)
                                                    .mapToObj(b -> new double[]{a, b, Math.sqrt((a*a + b*b))})
                                                    .filter(t -> t[2] % 1 ==0 ));
        pythagorean.limit(5)
                    .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
        pythagorean2.limit(5)
                    .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        
        /*
         * 값으로 Stream 생성
         * Stream.of( ... )
         * Arrays.stream( ... )
         */
        String[] strings = {"Hello", "World", "In", "Java"};
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        // Stream.of
        Stream<String> strStream = Stream.of(strings);
        Stream<Integer> intStream = Stream.of(1,2,3,4,5,6);
        strStream.map(String::toUpperCase).forEach(System.out::println);
        intStream.forEach(System.out::println);
        
        // Arrays.stream
        Arrays.stream(strings).forEach(System.out::print);
        System.out.println(Arrays.stream(numbers).average());
        System.out.println(Arrays.stream(numbers).sum());


        Stream<int[]> exam1 = Stream.of(numbers); // Stream<int[]> 형 반환
        Stream<Integer> exam2 = IntStream.of(numbers).boxed();
        Stream<Integer> exam3 = Arrays.stream(numbers).boxed();
        Stream.of(numbers).map(Arrays::stream).forEach(System.out::println);


    }   
}

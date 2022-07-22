package modern.practice;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import modern.Dish;

public class ii_DishPractice {

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

        /*
         * Stream은 1번만 소비된다.
         */
        List<String> threeHighCaloricDishNames = menu.stream()
                                                        .filter(dish -> dish.getCalories() > 300)
                                                        .map(Dish::getName)
                                                        .limit(3)
                                                        .collect(Collectors.toList());
        threeHighCaloricDishNames.forEach(System.out::println);

        List<String> title = Arrays.asList("java", "8");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        // s.forEach(System.out::println); // Exception 발생.


        /*
         * takeWhile, dropWhile
         */ 
        System.out.println("================== ALL ");
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                // .filter(dish -> dish.getCalories() < 500)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("================== dropWhile");
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .dropWhile(dish -> dish.getCalories() < 500)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("================== takeWhile");
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .takeWhile(dish -> dish.getCalories() < 500)
                // .skip(1)
                // .limit(3)
                // .map(dish -> dish.getCalories())
                .collect(Collectors.toList())
                .forEach(System.out::println);

        /*
         * flatMap : Stream 평면화
         */ 
        List<String> words = Arrays.asList("Hello", "World");
        
        // 1. 객체가 string[] 라서 문제 
        System.out.println("=================== 1");
        List<String[]> listStringArray = words.stream()
                .map(w -> w.split("")) // string[] 을 전달함
                .distinct()
                .collect(Collectors.toList());
        for(String[] item : listStringArray){
            Arrays.asList(item).forEach(System.out::print);
            System.out.println();
        }

        // 2. 각 배열을 별도의 스트림으로 생성
        System.out.println("=================== 2");
        List<Stream<String>> listStreamString = words.stream()
                .map(w -> w.split("")) // string[] 을 전달함
                .map(Arrays::stream) // 각 배열을 stream으로 생성
                .distinct()
                .collect(Collectors.toList());
        listStreamString.stream()
            .forEach(stream -> {
                stream.forEach(System.out::print);
                System.out.println();
            });

        // 3. flatMap 사용
        System.out.println("=================== 3");
        words.stream()
                .map(w -> w.split("")) // string[] 을 전달함
                .flatMap(Arrays::stream) // 생성 된 stream을 하나의 stream으로 평면화
                .distinct()
                .collect(Collectors.toList()) // List<String>
                .forEach(System.out::print);
        System.out.println();
    
        /*
         * 검색과 매칭
         * anyMatch, allMatch, noneMatch
         * short circuit 평가 방식을 사용함
         */ 
        if(menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("This group has more than one vegetarian menu.");
        }
        if(!menu.stream().allMatch(Dish::isVegetarian)){
            System.out.println("This group has no vegetarian menu.");
        }
        if(menu.stream().allMatch(d -> d.getCalories() < 1000))
            System.out.println("All of menus is under 1000 calories.");

        if(menu.stream().noneMatch(d -> d.getCalories() >= 1000))
            System.out.println("All of menus is under 1000 calories.");

        /*
         * findAny, findFirst
         * 병렬 실행에선 findAny를 사용한다.
         * Optional -> isPresent, ifPresent
         */
        Optional<Dish> dish = menu.stream()
                                    .filter(d -> d.getCalories() > 1000)
                                    .findAny();
        System.out.println(dish);

        menu.stream()
                .filter(d -> d.getCalories() > 1000)
                .findAny()
                .ifPresent(System.out::println); // 값이 없으면 수행하지 않음.

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.stream()
                    .map(n -> n * n)
                    .filter(n -> n % 3 == 0)
                    .findFirst()
                    .ifPresent(System.out::println);

        /*
         * reduce (초기값, 두 요소를 조합해서 새로운 값을 만드는 연산)
         *   a = 초기값
         *   b = stream에 있는 값
         *   연산 = a, b에 대해서 연산을 누적해서 실행한다.
         * 
         * 가변 누적자 패턴은 병렬처리에 거리가 멀다. reduce 같은 새로운 패턴이 필요하다.
         */
        System.out.println(numbers.stream().reduce(0, (a, b) -> a + b)); 
        System.out.println(numbers.stream().reduce(0, Integer::sum)); 
        
        System.out.println(numbers.stream().reduce(1, (a, b) -> a * b)); 
        System.out.println(numbers.stream().reduce((a, b) -> a * b));  // Optional

        // 최대값, 최소값
        System.out.println(numbers.stream().reduce(Integer::max)); 
        System.out.println(numbers.stream().reduce(Integer::min)); 

        // reduce pattern (map + reduce)
        System.out.println(menu.stream()
                                    .map(d -> 1)
                                    .reduce(0, Integer::sum));
        
    }
}
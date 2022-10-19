package book.modern.practice;

import book.modern.Apple;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class i_ModernJavaPractice {

    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<Apple>();
        inventory.add(new Apple("1", 100));
        inventory.add(new Apple("2", 100));
        inventory.add(new Apple("3", 200));
        inventory.add(new Apple("4", 20));
        inventory.add(new Apple("5", 300));
        inventory.add(new Apple("6", 300));
        inventory.add(new Apple("7", 400));
        inventory.add(new Apple("8", 40));

        // inventory.stream()
        //     .filter((Apple a) -> a.getWeight() > 100)
        //     .collect(Collectors.toList())
        //     .forEach(System.out::println);

        // inventory.sort(Comparator.comparing(Apple::getWeight)
        // .reversed()
        // .thenComparing(Apple::getName));

        // Predicate<Apple> predicateHeavyApple = (Apple a) -> a.getWeight() > 150;
        // List<Apple> result = filterAppleWeight(inventory, predicateHeavyApple
        //                                                     .and((Apple a) ->
        // a.getName().equals("3")));
        // System.out.println("=======================");
        // result.forEach(System.out::println);

        // AppleConstructor<String, Integer, Integer, Apple> apple = Apple::new;
        // apple.apply("tomato", 10, 100);

        List<String> temp =
                inventory.parallelStream()
                        .filter(apple -> apple.getWeight() > 100)
                        .sorted(Comparator.comparing(Apple::getWeight))
                        .map(Apple::getName)
                        .collect(Collectors.toList());
        temp.forEach(System.out::println);

        Map<Integer, List<Apple>> groupApple =
                inventory.stream().collect(Collectors.groupingBy(Apple::getWeight));
        System.out.println(groupApple);
    }

    static List<Apple> filterAppleWeight(List<Apple> list, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : list) {
            if (p.test(apple)) result.add(apple);
        }
        return result;
    }
    ;

    public interface AppleConstructor<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}

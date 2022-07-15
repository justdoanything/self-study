package src.modern;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DishPractice {
    
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

        // System.out.println(menu);

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


        // takeWhile, dropWhile
        System.out.println("================== ALL ");
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .filter(dish -> dish.getCalories() < 500)
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
    }
}

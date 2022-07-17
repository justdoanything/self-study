package src.modern.practice;

import java.util.Arrays;
import java.util.List;

import src.modern.Dish;

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
        
    }
}

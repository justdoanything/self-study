package modern.practice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import modern.Dish;

public class iv_CollectPractice {
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

        // counting
        System.out.println(menu.stream().collect(Collectors.counting()));
        
        // average
        System.out.println(menu.stream().collect(Collectors.averagingInt(Dish::getCalories)));
        System.out.println(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories)));
        
        // joining
        System.out.println(menu.stream().map(Dish::getName).collect(Collectors.joining("/")));
    }
}

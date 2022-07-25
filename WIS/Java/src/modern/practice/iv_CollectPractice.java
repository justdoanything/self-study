package modern.practice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import modern.Dish;
import modern.Trader;
import modern.Transaction;

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

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        // groupingBy
        Map<Integer, List<Transaction>> transactionsByYears = transactions.stream().collect(Collectors.groupingBy(Transaction::getYear));
        
        // counting
        System.out.println(menu.stream().collect(Collectors.counting()));
        
        // summingInt
        System.out.println(menu.stream().collect(Collectors.summingInt(Dish::getCalories)));

        // summarizingInt
        System.out.println(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)));
        System.out.println(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)).getSum());
        System.out.println(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)).getMax());
        System.out.println(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)).getAverage());

        // average
        System.out.println(menu.stream().collect(Collectors.averagingInt(Dish::getCalories)));
        System.out.println(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories)));
        
        // joining
        System.out.println(menu.stream().map(Dish::getName).collect(Collectors.joining("/")));

        // reducing를 이용한 값 비교
        System.out.println(menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
        System.out.println(menu.stream().reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        System.out.println(menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum)));

        System.out.println(menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum)));
    }
}

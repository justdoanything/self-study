import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaTest {
    
    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        inventory.add(new Apple("1", 100));
        inventory.add(new Apple("2", 10));
        inventory.add(new Apple("3", 200));
        inventory.add(new Apple("4", 20));
        inventory.add(new Apple("5", 300));
        inventory.add(new Apple("6", 30));
        inventory.add(new Apple("7", 400));
        inventory.add(new Apple("8", 40));

        inventory.stream()
            .filter((Apple a) -> a.getWeight() > 100)
            .collect(Collectors.toList())
            .forEach(System.out::println);
        
        inventory.sort(Comparator.comparing(Apple::getWeight)
        .reversed()
        .thenComparing(Apple::getName));

        Predicate<Apple> predicateHeavyApple = (Apple a) -> a.getWeight() > 150;
        List<Apple> result = filterAppleWeight(inventory, predicateHeavyApple
                                                            .and((Apple a) -> a.getName().equals("3")));
        System.out.println("=======================");
        result.forEach(System.out::println);
        

    }

    static List<Apple> filterAppleWeight(List<Apple> list, Predicate<Apple> p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : list){
            if(p.test(apple)) 
                result.add(apple);
        }
        return result;
    };

    
}

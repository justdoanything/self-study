import java.util.ArrayList;
import java.util.List;
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
            .filter((Apple a) -> a.getWeight() > 400)
            .collect(Collectors.toList())
            .forEach(System.out::println);
    }
}

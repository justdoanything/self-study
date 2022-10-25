package book.modern.practice;

import book.modern.Trader;
import book.modern.Transaction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class iii_TransactionPractice {
    public static void main(String[] args) {
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
                new Transaction(alan, 2012, 950));

        // 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        System.out.println("2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.");
        transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out::println);

        // 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        System.out.println("\n거래자가 근무하는 모든 도시를 중복 없이 나열하시오.");
        transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);

        // 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        System.out.println("\n케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.");
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);

        // 모든 거래자의 이름을 알파뱃순으로 정렬해서 반환하시오.
        System.out.println("\n모든 거래자의 이름을 알파뱃순으로 정렬해서 반환하시오.");
        transactions.stream()
                .map(t -> t.getTrader().getName())
                .sorted()
                // .sorted(Comparator.reverseOrder())
                .distinct()
                .forEach(System.out::println);

        // 밀리노에 거래자가 있는가?
        System.out.println("\n밀리노에 거래자가 있는가?");
        System.out.println(
                transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milano")) ? "Yes" : "No");

        System.out.println("\n밀란에 거래자가 있는가?");
        System.out.println(
                transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan")) ? "Yes" : "No");

        // 케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.
        System.out.println("\n케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.");
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getValue())
                .sorted()
                .forEach(System.out::println);

        // 전체 트랜잭션 중 최대값은 얼마인가?
        System.out.println("\n전체 트랜잭션 중 최대값은 얼마인가?");
        transactions.stream()
                .max(Comparator.comparing(Transaction::getValue))
                .ifPresent(t -> System.out.println(t.getValue()));

        // 다른 예시
        transactions.stream().map(Transaction::getValue).reduce(Integer::max).ifPresent(System.out::println);

        // 전체 트랜잭션 중 최솟값은 얼마인가?
        System.out.println("\n전체 트랜잭션 중 최솟값은 얼마인가?");
        transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .ifPresent(t -> System.out.println(t.getValue()));

        // 다른 예시
        transactions.stream()
                .map(Transaction::getValue)
                .reduce((t1, t2) -> t1 < t2 ? t1 : t2)
                .ifPresent(System.out::println);
        transactions.stream().mapToInt(Transaction::getValue).min().ifPresent(System.out::println);
    }
}

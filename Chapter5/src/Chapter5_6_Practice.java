import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Chapter5_6_Practice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions= Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );
        
        /***
         * 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
         */
        
        List<Transaction> tranOrder = transactions.stream()
                .filter(y->y.getYear()==2011)                                       //2011년에 발생한 트랜잭션을 필터링하도록 프레디케이트를 념겨줌
                .sorted(Comparator.comparing(Transaction::getValue))                //트랜잭션 값으로 요소 정렬
                .collect(Collectors.toList());
        System.out.println("tranOrder = " + tranOrder);


        /**
         *2. 거래자가 근무하는 모든 도시를 중복없이 나열하시오
         */
        List<String> traderCity = transactions.stream()
                .map(trader->trader.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("traderCity = " + traderCity);


        /**
         * 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오 
         */
        List<Trader> collect = transactions.stream()
                .map(trader -> trader.getTrader())
                .filter(t -> t.getCity() == "Cambridge")
                .distinct()
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);

        /**
         *  4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
         */
        List<Trader> collect1 = transactions.stream()
                .map(transaction -> transaction.getTrader())
                .sorted(Comparator.comparing(trader -> trader.getName()))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);

        /**
         * 5. 밀라노에 거래자가 있는가?
         */

        boolean b = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity() == "Milan");
        System.out.println("밀라노에 거래자가 있는가 ? : "+b);

        /**
         * 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오
         */

        List<Integer> collect2 = transactions.stream()
                .filter(t -> t.getTrader().getCity() == "Cambridge")
                .map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println("collect2 = " + collect2);

        /**
         * 7. 전체 트랜잭션 중 최댓값은 얼마인가 ?
         */
        Optional<Integer> reduce = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        reduce.ifPresent(re-> System.out.println("최댓값은" +re));

        /**
         * 8. 전체 트랜잭션 중 최솟값은 얼마인가?
         */

        Optional<Integer> reduce2 = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        reduce2.ifPresent(re-> System.out.println("최솟값은" +re));

    }
}

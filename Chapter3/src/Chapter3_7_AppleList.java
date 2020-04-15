

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;
/**
 * 람다 활용: 실행 어라운드 패턴
 */
public class Chapter3_7_AppleList {
    public static void main(String[] args) {
        /**
         * 1 단계: 코드 전달
         */
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(                 //inventory에 모두 추가 Arrays.asList Array형을 List형으로 변환
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));
        // [Apple{color=GREEN, weight=80}, Apple{color=RED, weight=120}, Apple{color=GREEN, weight=155}]
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little for test 2
        inventory.set(1, new Apple(30, Color.GREEN));

        /**
         * 2 단계: 익명 클래스 사용
         */
        //120이 사라지고 30이 들어옴
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {                //이 과정이 오름차순을 하는 과정
                return a1.getWeight() - a2.getWeight();             //a2, 와 a1의 자리를 바꾸면 내림차순이 됨  e.g.[Apple{color=GREEN, weight=155}, Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=30}]
            }
        });
        // [Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
        System.out.println(inventory);

        // reshuffling things a little for test 3
        inventory.set(1, new Apple(20, Color.GREEN));

        /**
         * 3 단계: 람다 표현식 사용
         */
        //80이 사라지고 20이 들어옴
        //3.2단계_ 람다의 파라미터 형식을 추론하기 때문에 밑에와 같이 사용가능
        inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
        System.out.println(inventory);

        // reshuffling things a little for test 3
        inventory.set(1, new Apple(15, Color.GREEN));

        //3.3 comparing 메서드 사용
        inventory.sort(comparing(apple -> apple.getWeight()));        //import static java.util.Comparator.*;를 해줬음.
        System.out.println("inventory = " + inventory);

        // reshuffling things a little for test 4
        inventory.set(1, new Apple(5, Color.GREEN));
        
        /**
         * 4 단계: 메서드 참조 사용 _ 제일 중요, 이렇게 코드가 짧아지고 의미가 명확해짐
         */
        //20이 사라지고 5가 들어옴
        inventory.sort(comparing(Apple::getWeight));
        System.out.println("inventory = " + inventory);

    }
    /**
     * 1단계 : 코드 전달
     */
    public static class AppleComparator implements Comparator<Apple>{
        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight() - a2.getWeight();
        }
    }
}

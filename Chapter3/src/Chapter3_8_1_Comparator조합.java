import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

public class Chapter3_8_1_Comparator조합 {
    public static void main(String[] args) {
        /**
         * 자바 8 API의 몇몇 함수형 인터페이스는 다양한 유틸리티 메서드를 포함한다. 이는 간단한 여러 람다 표현식을 조합해서
         * 더 어려운 람다 표현실을 만들 수 있다는 얘기이다. 여기서 디폴트 메서드(추상 메서드가 아니므로 함수형 인터페이스의 정의를 벗어나지 않음) 
         * 가 등장하는데, 이후에 다시 나옴   
         */

        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80 , Color.GREEN),
                new Apple(40 , Color.GREEN),
                new Apple(115 , Color.RED)
        ));

        //무게를 내림차순으로 정렬한다. reversed라는 디폴드 메스드를 제공하기때문
        inventory.sort(comparing(Apple::getWeight).reversed());
        System.out.println("inventory = " + inventory);


        /**
         * 무게가 같은 경우라면 어떻게 해야할까 ?, 2번째 comparator을 만들 수 있다.
         */
        //비교를 위해 같은 무게의 다른 색깔을 넣어봄
        inventory.add(new Apple(50, Color.GREEN));
        inventory.add(new Apple(50, Color.GREEN));
        inventory.add(new Apple(50, Color.RED));
        inventory.add(new Apple(50, Color.RED));

        //같은 무게라면 색깔로 판단
        inventory.sort(comparing(Apple::getWeight)
                    .reversed()
                    .thenComparing(Apple::getColor));       //두번째 연산자를 뜻함
        //inventory = [Apple{color=RED, weight=115}, Apple{color=GREEN, weight=80}, Apple{color=RED, weight=50}, Apple{color=RED, weight=50}, Apple{color=GREEN, weight=50}, Apple{color=GREEN, weight=50}, Apple{color=GREEN, weight=40}]
        System.out.println("inventory = " + inventory);


    }
}

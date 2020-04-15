import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Chapter3_8_2_Predicate조합 {

    public static void main(String[] args) {
        /**
         * Predicate를 사용해서 복잡한 프레디케이트를 만들 수 있다.
         *  negate, and, or 세가지 메서드를 제공 e.g."빨간색이 아닌 사과" 처럼 특정 프레디케이트를 반전 시킬때 negate사용
         *  이부분은 예제가 많이없어서 넘기겠다.
         */
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(160, Color.GREEN),
                new Apple(60, Color.GREEN),
                new Apple(160, Color.RED),
                new Apple(60, Color.RED)
        ));





    }
}

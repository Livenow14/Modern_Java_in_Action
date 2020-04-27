import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chapter5_7_NumericStreams {
    public static void main(String[] args) {
        
        //숫자 스트림으로 매핑
        int sum = Dish.menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("sum = " + sum);

        OptionalInt maxCalories = Dish.menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        //합계 예제에서는 0이라는 기본값이 있지만, 0이라는 기본값 때문에 잘못된 결과가 도출 되 수 있다.
        //요소가 없는 상황과 실제 최댓값이 0인 상황을 구별하기 위한 OptionalInt
        int max= maxCalories.orElse(1);
        System.out.println("max = " + max);

        // 숫자 범위
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        //피타고라스 수
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                        .map(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));
        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        Stream<int[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0))
                .map(array -> Arrays.stream(array).mapToInt(a -> (int) a).toArray());
        pythagoreanTriples2.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));


    }
}

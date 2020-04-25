import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Chapter5_5_Reducing {
    public static void main(String[] args) {
        
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        int sum = numbers.stream().reduce(0, (a,b)-> a+b);
        System.out.println("sum = " + sum);
        
        int sum2 = numbers.stream().reduce(0,Integer::sum);         //Integer클레스의 sum 메서드 사용
        System.out.println("sum2 = " + sum2);

        Optional<Integer> max= numbers.stream().reduce(Integer::max);       //최댓값을 찾음
        max.ifPresent(System.out::println);

        Optional<Integer> min= numbers.stream().reduce(Integer::min);       //최솟값을 찾음
        min.ifPresent(m -> System.out.println(m));

        //퀴즈 5.3 map과 reduce 메서드를 이용해서 스트림의 요리 개수를 계산하시오.
        int count = Dish.menu.stream()
                .map(d->1)      //스트림의 각 요소를 1로 매핑한다.
                .reduce(0, Integer::sum);       //reduce를 쓰기위한 에제이다. 맵 리듀스 패턴이라하며, 쉽게 병렬화하는 특징 덕분에
        System.out.println("count = " + count);         //구글이 웹 검색애 적용하면서 유명해졌다.

        //4장에서 배운 count()로 더 쉽게 할 수 있다.
        long count2 = Dish.menu.stream()
                .count();                       //스트림의 요소수를 센다.
        System.out.println("count2 = " + count2);



    }
}

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.*;

public class Chapter6_2_Summarizing {
    public static void main(String[] args) {
        //6.2
        Long howManyDishes = Dish.menu.stream().collect(Collectors.counting());
        //long howManyDishes = Dish.menu.stream().count(); 이것도 가능하다.  counting은 다른 컬렉터와 함께 사용할 때 강력하다.
        System.out.println("Number of dishes: "+ howManyDishes);

        //6.2.1 스트림 값에서 최댓값과 최솟값 검색
        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> MostCalorieDish = Dish.menu.stream().collect(maxBy(dishComparator));
        System.out.println("MostCalorieDish = " + MostCalorieDish);
        
        //6.2.2 요약연산
        //메뉴 리스트의 총 칼로리를 계산하는 코드.
        Integer totalCalories = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("totalCalories = " + totalCalories);
        
        //메뉴 리스트의 평균값 게산
        Double averageCalories = Dish.menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println("averageCalories = " + averageCalories);
        
        //summarizingInt기능 
        IntSummaryStatistics summaryStatistics = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("summaryStatistics = " + summaryStatistics);
        System.out.println("count :"+summaryStatistics.getMax());        //이런식으로 사용가능

        //6.2.3 문자열 연결
        String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining());
        System.out.println("shortMenu = " + shortMenu);
        
        //결과값이 이상하므로 두 요소 사이에 구분 문자열 넣기 
        String shortMenu2 = Dish.menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println("shortMenu2 = " + shortMenu2);
        
        //컬렉터를 사용하지 않고 sum 계산, 널문제를 피하기 위해, get 을 사용
        Integer reduceSum = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        System.out.println("reduceSum = " + reduceSum);

        //mapToInt사용
        int sum = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("sum = " + sum);

    }


}

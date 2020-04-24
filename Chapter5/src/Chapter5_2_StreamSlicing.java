import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Chapter5_2_StreamSlicing {
    public static void main(String[] args) {
        // 5.2.1 프레디 케이트를 이용한 스트림 슬라이스
        // 칼로리 값을 기준으로 리스트를 오름차순 정렬
        List<Dish> specialMenu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));
        System.out.println("Filtered sorted menu:");
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(toList());
        filteredMenu.forEach(System.out::println);
        
        //TakeWhile이용 자바 9에서 사용가능
        //DropWhile 또한 자바 9에서 사용가능


        // 5.2.2 스트림 축소 
        List<Dish> dishesLimit3= specialMenu.stream()
                .filter(dish -> dish.getCalories()>300)
                .limit(3)
                .collect(toList());
        System.out.println("dishesLimit3 = " + dishesLimit3);
        
        //5.2.3 요소 건너뛰기
        List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories()>300)
                .skip(2)
                .collect(toList());
        System.out.println("dishes = " + dishes);
     
        //퀴즈 5.1 스트림을 이용해서 처음 등장하는 두 고기 요리를 필터링 하시오
        List<Dish> meat2 = Dish.menu.stream()
                .filter(dish -> dish.getType()== Dish.Type.MEAT)
                .limit(2)
                .collect(toList());
        System.out.println("meat2 = " + meat2);
        
    }
        

}

import static java.util.stream.Collectors.toList;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  filter, map, limit는 서로 연결되어 파이프라인을 형성한다.
 *  collect로 파이프라인을 실행한 다음에 닫는다.
 *
 *  연결할 수 있는 스트림 연산을 중간 연산이라고 하며, 스트림을 닫는 연산을 최종 연산이라고 한다.
 *
 */
public class HighCaloriesNames {

    public static void main(String[] args) {

        /**
         * 이와 같은 디버깅 코드는 디버깅 할때만 사용하고, 제품 코드에는 안하는것이 좋다.
         * 스트림의 게으른 특성 덕분에 몇 가지 최적화 효과를 얻었다. 첫째,300칼로리가 넘는 요리는 여러 개지만 오직 처음 3개만 선택되었다.
         * 이는 limit 연산 그리고 "쇼트서킷"이라 불리는 기법 덕분이다. 둘째, filter와 map은 서로 다른 연산이지만 한과정으로 변합되었다.
         * (이 기법을 "루프 퓨전"이라고 한다)
         */
        //내부 반복
        List<String> names = Dish.menu.stream()
                .filter(dish -> {                                                   //중간연산
                    System.out.println("filtering " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {                                                      //중간연산
                    System.out.println("mapping " + dish.getName());
                    return dish.getName();
                })
                .limit(3)                                                           //중간연산
                .collect(toList());                                                 //최종연산
        System.out.println(names);
    }

}

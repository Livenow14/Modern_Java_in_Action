import java.util.List;
import java.util.stream.Collectors;


/**
 *  스트림에서 메뉴를 필터링해서 세 개의 고칼로리 요리명 찾기
 *  요리 리스트를 포함하는 menu에 stream 메서드를 호출해서 스트림을 얻엇다. 여기서 "데이터 소스"는 요리 리스트(메뉴)이다. 데이터 소스는 "연속된 요소"를 스트림에 제공한다.
 *  다음으로 스트림에 filter, map, limit, collect로 이어지는 일련의 "데이터 처리 연산"을 적용한다.
 *  collect를 제외한 모든 연산은 서로 "파이프라인"을 형성할 수 있도록 스트림을 반환한다. 파이프 라인은 소스에 적용하는 질의 같은 존재다.
 *  마지막으로 collect 연산으로 파이프라인을 처리해서 결과를 반환한다.(collect는 스트림이 아니라 List를 반환). 마지막에 collect를 호출하기 전까지는
 *  menu에서 무엇도 선택되지 않으며 출력 결과도 없다. 즉, collect가 호출되기 전까지 메서드 호출이 저장되는 효과가 있다.
 *
 */

public class ThreeHighCaloricDishNames {
    public static void main(String[] args) {
        List<String> threeHighCaloricDishNames =
                Dish.menu.stream()                                  // 메뉴(요리 리스트)에서 스트림을 얻는다.                       Stream<Dish>
                    .filter(dish -> dish.getCalories()>300)         // 파이프라인 연산 만들기, 첫 번째로 고칼로리 요리를 필터링한다   Stream<Dish>
                    .map(Dish::getName)                             // 요리명 추출                                                Stream<String>
                    .limit(3)                                       // 선착순 세 개만 선택                                         Stream<String>
                    .collect(Collectors.toList());                  // 결과를 다른 리스트로 저장                                    List<String>

        System.out.println(threeHighCaloricDishNames);              // [pork, beef, chicken
    }
}

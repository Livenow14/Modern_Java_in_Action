import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Chapter5_3_Mapping {
    public static void main(String[] args) {
        
        //5.3.1 스트림의 각 요소에 함수 적용하기
        List<String> dishNames = Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println("dishNames = " + dishNames);

        //map을 이용 글자수 추출
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLenghts = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("wordLenghts = " + wordLenghts);
        
        //map을 이용, 각 메뉴의 글자수 추출
        List<Integer> dishNameLength = Dish.menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("dishNameLength = " + dishNameLength);

        //flatMap을 사용해서 중복없이 문자열 나누기
        List<String> words2=Arrays.asList("Hello", "World");
        List<String> uniqueCharacter = words2.stream()
                .map(word->word.split(""))                 //각 단어를 개별 문자를 포함하는 배열로 변환
                .flatMap(Arrays::stream)                         // 생성된 스트림을 하나의 스트림으로 평면화
                .distinct()
                .collect(Collectors.toList());
        System.out.println("uniqueCharacter = " + uniqueCharacter);

        //퀴즈 5.2.1 숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환하시오
        //예를 들어 [1,2,3,4,5]가 주어지면  [1, 4, 9, 16, 25]를 반환해야 한다.
        List<Integer> numbers= Arrays.asList(1,2,3,4,5);
        List<Integer> squareNumbers = numbers.stream()
                .map(number-> number*number)
                .collect(Collectors.toList());
        System.out.println("squareNumbers = " + squareNumbers);

        //퀴즈 5.2.2 두 개의 숫자 리스트가 있을 때 모든 숫자 쌋의 리스트를 반환하시오
        //예를 들어 두 개의 리스트 [1, 2, 3]과 [3, 4]가 주어지면 [(1,3),(1,4) ...(3,4)]를 반환해야한다.
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream()
                        .map((Integer j) -> new int[]{i, j})
                )
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)                       //이것을지우면 전체 요소에 대하여 배열이 생김
                .collect(toList());
        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
    }
}

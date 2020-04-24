import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Chapter5_1_Filtering {

    public static void main(String[] args) {

        // 5.1.1 프레디케이트로 거름
        System.out.println("-Filtering with a predicate");
        List<Dish> vegetarianMenu = Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        System.out.println("vegetarianMenu = " + vegetarianMenu);
        //vegetarianMenu.forEach(System.out::println);
        System.out.println();

        // 5.1.2 고유 요소 필터링
        System.out.println("-Filtering with unique elements");
        List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
        numbers.stream()
                .filter(i->i%2 ==0)
                .distinct()                                     //중복 제거
                .forEach(System.out::println);


    }

}

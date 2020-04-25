import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Chapter5_4_Finding {
    public static void main(String[] args) {
        if(isVegetarianFriendlyMenu()){
            System.out.println("Vegetarian friendly");
        }
        System.out.println("isHealthyMenu?(<1000) : "+ isHealthyMenu());
        System.out.println("isHealthyMenu?(>1000) : "+ isHealthyMenu2());

        Optional<Dish> dish = findVegetarianDish();
        dish.ifPresent(d-> System.out.println(d.getName()));    //값이 있으면 출력되고 없으면 아무일도 일어나지 않는다.

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree= someNumbers.stream()
                                                .map(n->n*n)
                                                .filter(n->n%3 ==0)
                                                .findFirst();       //9
        firstSquareDivisibleByThree.ifPresent(d-> System.out.println(d));

    }

    private static boolean isVegetarianFriendlyMenu() {
        return Dish.menu.stream().anyMatch(Dish::isVegetarian);     //menu에 채식요리가 잇는지 확인
    }
    private static boolean isHealthyMenu(){
        return Dish.menu.stream().allMatch(d-> d.getCalories()<1000);   //모든 요소의 칼로리가 1000이하인지 체크
    }
    private static boolean isHealthyMenu2(){
        return Dish.menu.stream().noneMatch(d-> d.getCalories()>1000);   //모든 요소의 칼로리가 1000이상이 아닌지 체크
    }

    private static Optional<Dish> findVegetarianDish(){
        return Dish.menu.stream()
                .filter(Dish::isVegetarian)             //Optional<Dish>반환
                .findAny();
    }
}

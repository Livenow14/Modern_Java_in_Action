import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {

    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED),
                new Apple(160, Color.RED)
        );


        List<Apple> greenApples = filterApplesByColor(inventory, Color.GREEN);              //2.1.2 두번째 시도 : 색을 파라미터화
        System.out.println(1);
        System.out.println(greenApples);


        List<Apple> redApples = filterApplesByColor(inventory, Color.RED);                //2.1.2 두번째 시도 : 색을 파라미터화
        System.out.println(2);
        System.out.println(redApples);


        List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());                //추상적 조건을 메소드 인자로 넘김
        System.out.println(3);
        System.out.println(greenApples2);


        List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
        System.out.println(4);
        System.out.println(heavyApples);


        List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(5);
        System.out.println(redAndHeavyApples);


        List<Apple> redApples2 = filter(inventory, new ApplePredicate() {                   //2.3.2 다섯 번째 시도 : 익명 클래스 사용
            @Override
            public boolean test(Apple a) {
                return a.getColor() == Color.RED;
            }
        });
        System.out.println(6);
        System.out.println(redApples2);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {                        //2.1.1 첫번째 시도 : 녹색 사과 필터링
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor() == Color.GREEN) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {         //2.1.2 두번째 시도 : 색을 파라미터화
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor() == color) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {     //2.1.2 네번째 시도 : 추상적 조건으로 필터링
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    enum Color {
        RED,
        GREEN
    }

    public static class Apple {

        private int weight = 0;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @SuppressWarnings("boxing")
        @Override
        public String toString() {
            return String.format("Apple{color=%s, weight=%d}", color, weight);
        }

    }

    interface ApplePredicate {                                                          //선택  조건을 결정하는 인터페이스를 정의 / 참 또는 거짓을 변환하는 함수를 프레디 케이트라고 함

        boolean test(Apple a);

    }

    static class AppleWeightPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }

    }

    static class AppleColorPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getColor() == Color.GREEN;
        }

    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getColor() == Color.RED && apple.getWeight() > 150;
        }

    }

}

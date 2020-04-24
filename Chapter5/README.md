 # Chapter5
 
 ## 5.1 필터링
 
 ### 5.1.1 프레디케이트로 필터링 
 filter메서드는 프레디케이트(불리언을 반환하는 함수)를 인수를 받아서 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.  
 
  ```groovy
 ex) 모든 채식요리를 필터링해서 채식 메뉴만들기  
List<Dish> vegetarianMenu = Dish.menu.stream()
                                        .filter(Dish::isVegetarian) //채식요리인지 확인하는 메서드 참조    
                                        .collect(toList());
  ``` 

 ### 5.1.2 고유 요소 필터링
 스트림은 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다.(고유 여부는 스트림에서 만든 객체의 hashCode,
  equals로 결정된다).
  
   ```groovy
   ex) 모든 짝수를 선택하고 중복을 필터링 
    System.out.println("Filtering unique elements:");
    List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
    numbers.stream()
        .filter(i -> i % 2 == 0)
        .distinct()
        .forEach(System.out::println);
  ``` 

 ## 5.2 스트림 슬라이싱
 ### 5.2.1 프레디케이트를 이용한 슬라이싱
 자바 9에서는 TakeWhile, DropWhile을 지원한다. 
   
 ### 5.2.2 스트림 축소
 스트림은 주어진 값 이하의 크기를 갖는 새로운 스트림을 반호나하는 limit(n)메서드를 지원한다. 스트림이 정렬되어 있으면 
 최대 요소 n개를 반환할 수 있다.
 
   ```groovy
ex) limit를 이용한 축소 
   List<Dish> dishesLimit3= specialMenu.stream()
                 .filter(dish -> dish.getCalories()>300)
                 .limit(3)
                 .collect(toList());
   System.out.println("dishesLimit3 = " + dishesLimit3);
  ```
 
 정렬되지 않은 스트림에서도 limit를 사용할 수 있다. 소스가 정렬되어 있지 않았다면 limit의 결과도 정렬되지 않은 상태로 반환된다.
 
  ### 5.2.3 요소 건너뛰기

스트림은 처음 n개 요소를 제외한 스트림을 반환하는 skip(n)메서드를 지원한다. n개 이하의 요소를 포함하는 스트림에 skip(n)을 호출하면
빈 스트림이 반환된다. limit(n)과 skip(n)은 상호 보완적인 연산을 수행한다. 
 
 ```groovy
ex) 처음 300칼로이 이상의 요리 두개를 건너뛴 다음 300칼로리 이상의 요리 반환 
 List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories()>300)
                .skip(2)
                .collect(toList());
 System.out.println("dishes = " + dishes);

```
 
 
 


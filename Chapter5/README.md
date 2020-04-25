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
 
  ## 5.3 매핑
  특정 객체에서 특정 데이터를 선택하는 작업은 데이터 처리 과정에서 자주 수행되는 연산이다. 예를 들어 SQL의 테이블에서
  특정 열만 선택할 수 있다. 스트림 API의 map 과 flatMap 메서드는 특정 데이털르 선택하는 기능을 제공한다. 
  
  ### 5.3.1 스트림의 각 요소에 함수 적용하기 
  스트림은 함수를 인수로 받는 map 메서드를 지원한다. 인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가
  새로운 요소로 매핑된다(이 과정은 기존의 값을 '고친다'라는 개념보다는 '새로운 버전을 만든다'라는 개념에 가까우므로 '변환'에 가까운
  '매핑'이라는 단어를 사용한다). 예를 들어 다음은 Dish::getName을 map 메서드로 전달해서 스트림의 요리명을 추출하는 코드이다. 
 
```groovy
ex) 요리명 추출 
 List<String> dishNames = Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
 System.out.println("dishNames = " + dishNames);

```
 getName은 문자열을 반환하므로 map 메서드의 출력 스트림은 Stream<String> 형식을 갖는다.   
 다른 예제를 살펴보면 map 메서드가 어떤 일을 수행하는지 더 잘 이해할 수 있다. 단어 리스트가 주어졌을 때 각 단어가 포함하는 그자 수의
 리스트를 반환한다고 하자. 어떻게 이 작업을 구현할 수 있을까? 리스트의 각 요소에 함수를 적용해야 한다. 
 이는 map을 사용할 수 있다.각 요소에 적용할 함수는 단어를 인수로 받아서 길이를 반환해야 한다.
 결론적으로 다음처럼 메서드 참조 String::lenght를 map에 전달해서 문제를 해결 할 수 있다.  
 ```groovy
ex) 글자수 추출 
 List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
 List<Integer> wordLenghts = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
 System.out.println("wordLenghts = " + wordLenghts);

```
 다시 요리명을 추출하는 예제를 돌아가자. 각 요리명의 길이를 알고 싶다면 어떻게 해야 할까? 다음처럼 map 메서드를 연결 할 수 있다. 
```groovy
ex) 요리명 글자수 추출 
List<Integer> dishNameLength = Dish.menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
System.out.println("dishNameLength = " + dishNameLength);
```

  ### 5.3.2 스트림 평면화 
  메서드 map을 이용해서 리스트의 각 단어의 길이를 반환하는 방법을 확인했다. 이를 응용해서 리스트에서 고유 문자로 이루어진 리스트를 
  반환해보자. 예를 들어 ["Hello", "World"] 리스트가 있다면 결과로 ["H","e","l","l","o","W","o","r","l","d" ]를 포함하는 리스트가
  변환 되어야한다.   
  리스트에 있는 각 단어를 문자로 매핑한 다음에 distinct로 중복된 문자를 필터링 해서 쉽게 문제를 해결할 수 있을 것이라고 추측한 
  독자들도 있을 것이다. 즉, 다음처름 문제를 해결할 수 있다. 
  ```groovy
    words.stream()
        .map(word->word.split(""))
        .distinct()
        .collect(toList());
```
  하지만 위코드에서 map으로 전달한 람다는 각 단어의 String[](문자열 배열)을 반환한다는 점이 문제다. 따라서 map 메소드가 
  반환한 스트림의 형식은 Stream<String[])>이다. 우리가 원하는 것은 문자열의 스트림을 표현할 Stream<String>이다.   
  다행히 flatMap이라는 메서드를 이용해서 이 문제를 해결할 수 있다. 
  
  -flatMap 사용
   ```groovy
 List<String> words2=Arrays.asList("Hello", "World");
 List<String> uniqueCharacter = words2.stream()
                .map(word->word.split(""))                 //각 단어를 개별 문자를 포함하는 배열로 변환
                .flatMap(Arrays::stream)                         // 생성된 스트림을 하나의 스트림으로 평면화 
                .distinct()
                .collect(Collectors.toList());
 System.out.println("uniqueCharacter = " + uniqueCharacter);
```

  ## 5.4 검색과 매칭
  특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리도 자주 사용된다. 스트림 API는 allMatch, anyMatch, noneMatch, findFirst
  , findAny 등 다양한 유틸리티 메서드를 제공한다. 
  ### 5.4.1  프레디케이트가 적어도 한 요소와 일치하는지 확인 
  적어도 한요소가 일치하는지 확인할 때 anyMatch 메서드를 사용한다.
  ```groovy
 private static boolean isVegetarianFriendlyMenu() {
         return Dish.menu.stream().anyMatch(Dish::isVegetarian);     //menu에 채식요리가 잇는지 확인 
}
```
 ### 5.4.2 프레디케이트가 모든 요소와 일치하는지 검사 
 allMatch 메서드는 스트림의 모든요소가 프레디케이트와 일치하는지 검사한다. 
 ```groovy
private static boolean isHealthyMenu(){
        return Dish.menu.stream().allMatch(d-> d.getCalories()<1000);   //모든 요소의 칼로리가 1000이하인지 체크
}
```

 ### noneMatch
 allMatch와 반대 연산을 수행한다. 즉, 주어진 프리디케이트와 일치하는 요소가 없는지 확인한다. 
 ```groovy
private static boolean isHealthyMenu2(){
        return Dish.menu.stream().noneMatch(d-> d.getCalories()>1000);   //모든 요소의 칼로리가 1000이상이 아닌지 체크
}
```
anyMatch, allMatch, noneMatch 세 메서드는 스트림 쇼트서킷 기법, 즉 자바의 &&, ||와 같은 연산을 활용한다.  
예를들어 and연산으로 연결된 커다란 불리언 표현식을 평가한다고 가정한다면, 표현식에서 하나라도 거짓이라는 결과가 나오면 나머지 표현식의 결과가 
상관없이 전체 결과도 거짓이 된다. 이러한 상황을 쇼트셔킷이라고함 .

 ### 5.4.3 요소 검색
 findAny 메서드는 현재 스트림에서 임의의 요소를 반환한다. findAny 메서드를 다른 스트림연산과 연결해서 사용할 ㅅ ㅜ있다. 
 예를들어 다음 코드처럼 filter와 findAny를 이용해서 채식요리를 선택할 수 있다.
  
  ```groovy

Optional<Dish> dish = findVegetarianDish();
dish.ifPresent(d-> System.out.println(d.getName()));

private static Optional<Dish> findVegetarianDish(){
        return Dish.menu.stream().filter(Dish::isVegetarian).findAny();
}
```
스트림 파이프라인은 내부적으로 단일 과정으로 실행할 수 있도록 최적화된다. 즉, 쇼트서킷을 이용해서 결과를 찾는 즉시
실행을 종료한다. 

 ### Optional 이란?
 Optional<T> 클래스는 값의 존재나 부재 여부를 표현하는 컨테이너 클래스다. 이전 예제에서 findAny는 아무 요소도 반환하지 않을 수 있다.
 null은 쉽게 에러를 일으킬 수 있으므로 자바 8 라이브러리 설계자는 Optional<T>를 만들었다. Optional을 이용해서
 null 확인 관련 버그를 피하는 방법은 10장에 자세히 설명한다. 일단 Optional은 값이 존재하는지 확인하고 값이 없을 때 어떻게 처리할지
 강제하는 기능을 제공한다. 
 - isPresent() : Optional이 값을 포함하면 true 을 반환하고, 값을 포함하지 않으면 false를 반환한다.
 - ifPresent(Consumer<T> block) : 값이 있으면 주어진 블록을 실행한다. Consumer 함수형 인터페이스에는
 T 형식의 인수를 받으며 void를 반환하는 람다를 전달할 수 있다. 
 - T get() : 값이 존하면 값을 반환하고, 값이 없으면 NoSuchElementException을 일으킨다.
 - t orElse(T other)는 값이 있으면 값을 반환하고, 값이 없으면 기본값을 반환한다. 
 
 예를들어 이전 예제의 Optional<Dish>에서는 요리명이 null인지 검사할 필요가 없었다. 
 
 ### 5.4.4 첫번째 요소 찾기 
 일부 스트림에는 논리적인 아이템 순서가 정해져 있을 수 있다. 이런 스트림에서 첫번째 요소를 찾으려면 findFirst를 사용하면된다.
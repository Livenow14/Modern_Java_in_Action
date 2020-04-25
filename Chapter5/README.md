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
 ```groovy
List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> firstSquareDivisibleByThree= someNumbers.stream()
                                                .map(n->n*n)
                                                .filter(n->n%3 ==0)
                                                .findFirst();       //9
firstSquareDivisibleByThree.ifPresent(d-> System.out.println(d));

```
 
 ## 5.5 리듀싱 
 '메뉴의 모든 칼로리의 합계를 구하시오', '메뉴에서 칼로리가 가장 낮은 요리는?' 같이 스트림 요소를 조합해서 더 복잡한 질의를 표현하는
 방법을 설명한다. 이러한 질의를 수행하려면 Integer 같은 결과가 나올 때까지 스트림의 모든 요소를 반복적으로 처리해야 한다. 
 이런 질의를 리듀싱 연산(모든 스트림 요소를 처리해서 값으로 도출하는)이라고 한다. 함수형 프로그래밍 언어 용어로는 이 과정이 마치 
 종이(우리의 스트림)를 작은 조각이 될때까지 반복해서 접는 것과 비슷하다는 의미로 폴드라고 부른다. 
 
 ### 5.5.1 요소의 합 
 reduce는 두 개의 인수를 갖는다.
 - 초기값 0
 - 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>, 예제에서는 람다 표현신 (a,b)-> a+b를 사용했다. 
 ```groovy
List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
int sum = numbers.stream().reduce(0, (a,b)-> a+b);
System.out.println("sum = " + sum); //15

```
어떤 식으로 reduce가 스트림의 모든 숫자를 더하는지 자세히 살펴보자. 우선 람다의 첫 번째 파라미터 (a)에 0이 사용되었고,
스트림에서 3을 소비해서 두번째 파라미터(b)로 사용햇다. 0+3의 결과인 3이 새로운 누적값(accumulated value)이 되었다.
이제 누적값으로 람다를 다시 호출하여 다음 요소인 4를(b) 소비한다. 이렇게 최종적으로 15가 도출된다.  
메서드 참조를 이용해서 이 코드를 더 간결하게 만들 수 있다. 자바 8에서는 Integer 크래스에 두 숫자를 더하는 정적 sum 메서드를
제공한다. 따라서 직접 람다 코드를 구현할 필요가 없다. 
```groovy
List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
int sum2 = numbers.stream().reduce(0,Integer::sum);         //Integer클레스의 sum 메서드 사용 
System.out.println("sum2 = " + sum2);
        
``` 

### 초깃값이 없을 때 
초깃값을 받지 않도록 오버로드된 reduce도 있다. 그러나 이 reduce는 Optional 객체를 반환한다.   
Optional<Integer>을 반환하는 이유는, 스트림에 아무 요소도 없는 상황이 있을 수 있기 때문이다. 이때 초깃값이 없으므로 reduce는
합계를 반환할 수 없다. 따라서 합계가 없음을 가르킬 수 있도록 Optional 객체로 감싼 결과를 반환한다. 

### 5.5.2 최댓값과 최솟값 
최댓값과 최솟값을 찾을 때도 reduce를 활용할 수 있다. 앞서 말했듯이 reduce는 두 인수를 받는다.
- 초깃값
- 스트림의 두 요소를 합쳐서 하나의 값으로 만드는데 사용할 람다

```groovy
List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
Optional<Integer> max= numbers.stream().reduce(Integer::max);       //최댓값을 찾음
max.ifPresent(System.out::println);

Optional<Integer> min= numbers.stream().reduce(Integer::min);       //최솟값을 찾음
min.ifPresent(m -> System.out.println(m));
```

### reduce 메서드의 장점과 병렬화
reduce를 이용하면 내부 반복이 추상화 되면서 내부 구현에서 병렬로 reduce를 실행할 수 있게된다. 반복적인 합계에서는 sum변수를 
공유해야 하므로 쉽게 병렬화하기 어렵다. 강제적으로 동기화시키더라도 결국 병렬화로 얻어야 할 이득이 스레드 간의 소모적인 경쟁때문에
상쇄되어 버린다는 사실을 알게 될 것이다. 사실 이 작업을 병렬화 하면 입력을 분할하고, 분할된 입력을 더한 다음에, 더한 값을 합쳐야 한다.
지금까지 살펴본 코드와는 조금 다른 코드가 나타난다. 포크/조인 프레임워크를 이용하는 방법을 알아야한다. paralledStream()을 사용해서
병렬처리 할 수 있다. 하지만 이에 대한 대가도 필요하다. 

### 스트림 연산 : 상태 있음과 없음 
스트림 연산은 마치 만병통치약 같은 존재다. 스트림을 이용해서 원하는 연산을 쉽게 구현할 수 있으며 컬렉션으로 스트림을 만드는
stream 메서드를 parallelStream로 바꾸는 것만으로도 별다른 노력 없이 병렬성을 얻을 수 있다. 우리 예제에서
사용한 기법을 많은 애플리케이션에서도 이용한다. 요리 리스트를 스트림으로 변환할 수 있고, filter로 원하는 종류의 요리만 선택할 수 있으며,
map을 이용해서 칼로리를 추가한 다음에, reduce로 요리의 칼로리 총합을 계산한다. 심지어 이런 계산을 병렬로 실행할 수 있다. 
하지만 이들은 각각 다양한 연산을 수행한다. 따라서 각각의 연산은 내부적인 상태를 고려해야한다.  
map, filter 등은 입력 스트림에서 각 요소를 받아 0 또는 결과를 출력 스트림으로 보낸다. 따라서(사용자가 제공한 람다나 메서드 참조가 내부적인 가변
상태를 갖지 않는다는 가정하에)이들은 보통 상태가 없는, 즉 내부 상태를 갖지 않은 연산(stateless operation)이다.  
하지만 reduce, sum, max 같은 연산은 결과를 누적할 내부 상태가 필요하다. 예제으 ㅣ내부 상태는 작은 값이다.
우리 예제에서는 int 또는 double을 내부 상태롤 사용했다. 스트림에서 처리하는 요소 수와 관계없이 내부 상태의 크기는 한정(bounded)되어있다.  
반변 sorted나 distinct 같은 연산은 filter나 map처럼 스트림을 입력으로 받아 다름 스트림을 출력하는 것처럼 보일 수 있다. 하지만 sorted
나 distinct는 filter나 map과는 다르다. 스트림의 요소를 정렬하거나 중복을 제거하려면 과거의 이력을 알고 있어야 한다.
예를 들어 어떤요소를 출력 스트림으로 추가하려면 "모든 요소가 버퍼에 추가되어 있어야 한다". 연산을 수행하는데 필요한 저장소 크기는
정해져 있지 않다. 따라서 데이터 스트림의 크기가 크거나 무한이라면 문제가 생길 수 있다.(예를 들어 모든 소수를 포함하는 스트림을 
역순으로 만들면 어떤일이 일어날까? 첫 번째 요소로 가장 큰 소스, 즉 세상에서 존재하지 않는 수를 반환해야 한다). 이러한 연산을
"내부상태를 갖는 연산"이라 한다. 


 

 

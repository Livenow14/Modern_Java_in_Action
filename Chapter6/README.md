 # Chapter6 
 
 collect와 컬렉터로 구현할 수 있는 질의 예제.
 - 통화별로 트랜잭션을 그룹화한 다음에 해당 통화로 일어난 모든 트랜잭션 합계를 계산하시오(<Map<Currency, Integer> 반환).
 - 트랜잭션을 비싼 트랜잭션과 저렴한 트랜잭션 두 그룹으로 분류히사오(Map<Boolean, List<Transaction>>) 반환).
 - 트랜잭션을 도시 등 다수준으로 그룹화 하시오. 그리고 각 트랜잭션이 비싼지 저렴한지 구분하시오(Map<String, Map<Boolean, List<Transaction>> 반환).
 
 ```groovy
//범용적인 컬렉터 파라미터를 collect 메서드에 전달.
private static void groupFunctionally() {
        Map<Currency, List<Transaction>> transactionsByCurrencies = transactions.stream()
                .collect(groupingBy(Transaction::getCurrency));
System.out.println(transactionsByCurrencies);
    }
```
 
 
## 6.1 컬렉터란 무엇인가?
위 예제는 명령형 프로그래밍에 비해 함수형 프로그래밍이 얼마나 편리한지 명확하게 보여준다. 함수형 프로그래밍에서는 '무엇'을 원하는지
직접 명시할 수 있어서 어떤 방법으로 이를 얻을지는 신경 쓸 필요가 없다.   
여기서는 groupingBy를 이용해서 '각 키(통화) 버킷 그리고 각 키 버킷에 대응하는 요소 리스트를 값으로 포함하는 맵을 만들라'는 동작을 수행한다. 

### 6.1.1 고급 리듀싱 기능을 수행하는 컬렉터 
훌륭하게 설계된 함수형 API의 또 다른 장점으로 높은 수준의 조합성과 재사용성을 꼽을 수 있다. collect로 결과를 수집하는 과정을 간단하면서도
유연한 방식으로 정의할 수 있다는 점이 컬렉터의 최대 강점이다. 구체적으로는 스트림에 collect를 호출하면 스틀미의 요소에(컬렉터로 파라미터화된) 
리듀싱 연산이 수행된다.  
Colletor 유틸리티 클래스는 자주 사용하는 컬렉터 인스턴스를 손쉽게 생설할 수 있는 정적 
팩토리 메서드를 제공한다. 예를 들어 가장 많이 사용하는 직관적인 정적 메서드로 toList를 꼽을 수 잇다. toList는 스트림의 모든요소를 리스트로 수집한다.

### 6.1.2 미리 정의된 컬렉터
6장에서는 미리 정의된 컬렉터, 즉 groupingBy 같이 Collectors 클래스에서 제공하는 팩토리 메서드 기능을 설명한다. Coolectors에서 제공하는
메서드의 기능은 크게 세가지로 구분할 수 있다.
- 스트림 요소를 하나의 값으로 리듀스하고 요약
- 요소 그룹화
- 요소 분할


## 6.2 리듀싱과 요약
counting()이라는 팩토리 메서드가 반환하는 컬렉터로 메뉴에서 요리 수를 계산
```groovy
Long howManyDishes = Dish.menu.stream().collect(Collectors.counting());
//long howManyDishes = Dish.menu.stream().count(); 이것도 가능하다.  counting은 다른 컬렉터와 함께 사용할 때 강력하다.
System.out.println("Number of dishes: "+ howManyDishes);
```

### 6.2.1 스트림값에서 최댓값과 최솟값 검색
Collectors.maxBy, Collector.minBy 두 메소드를 이용한다. 두 커렉터는 스트림의 요소를 비교하는데 사용할 Comparator를 인수로 받는다. 
```groovy
스트림 값에서 최댓값과 최솟값 검색
Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);
Optional<Dish> MostCalorieDish = Dish.menu.stream().collect(maxBy(dishComparator));
System.out.println("MostCalorieDish = " + MostCalorieDish);
        
```

### 6.2.2 요약 연산 
summingInt는 객체를 int로 매핑하는 함수를 인수로 받는다. summinbInt의 인수로 전달된 함수는 객체를 int로 매핑한 컬렉터를 반환한다.
그리고 summingInt가 collect 메서드로 전달되면 요약 작업을 수행한다. 
```groovy
//메뉴 리스트의 총 칼로리를 계산하는 코드.
Integer totalCalories = Dish.menu.stream().collect(summingInt(Dish::getCalories));
System.out.println("totalCalories = " + totalCalories);
```
단순 합계외에 평균값 계산 등의 연산도 요약 기능으로 제공된다. 
```groovy
//메뉴 리스트의 평균값 게산
Double averageCalories = Dish.menu.stream().collect(averagingInt(Dish::getCalories));
System.out.println("averageCalories = " + averageCalories);
```

여려 연산을 수행해야 할 시에는 팩토리 메서드 summarizingInt가 반환하는 컬렉터를 사용할 수 있다. 
```groovy
//summarizingInt기능 
IntSummaryStatistics summaryStatistics = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
System.out.println("summaryStatistics = " + summaryStatistics);
System.out.println("count :"+summaryStatistics.getMax());        //이런식으로 사용가능
```

### 6.2.3 문자열 연결
컬렉터에 joining 팩토리 메서드를 이용하면 스트림의 각 객체에 toString 메서드를 호출해서 추출한 모든 문자열을하나의 문자열로 
연결해서 반환한다. 즉 다음은 메뉴의 모든 요리명을 연결하는 코드이다. 
```groovy
//6.2.3 문자열 연결
String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining());
System.out.println("shortMenu = " + shortMenu);
```

joining 메서드는 내부적으로 StringBuilder를 이용해서 문자열을 하나로 만든다. Dish 클래스가 요리명을 반환하는 toString 메서드를 포함하고 있다면
다음 코드에서 보여주는 것처럼 map으로 각 요리 이름을 추출하는 과정을 생략할 수 있다. (여기선 안됨 )

문자열을 연결하였지만 해석할 수 없다. 그렇기 때문에 연결된 두 요소 사이에 구분 문자열을 넣을 수 있도록 오버로드된 joining 팩토리 메서드도 있다.
따라서 다음 코드처럼 요리명 리스트를 콤마로 구분할 수 있다.
```groovy
//결과값이 이상하므로 두 요소 사이에 구분 문자열 넣기 
String shortMenu2 = Dish.menu.stream().map(Dish::getName).collect(joining(", "));
System.out.println("shortMenu2 = " + shortMenu2);
```

### 6.2.4 범용 리듀싱 요약 연산
### collect와 reduce
지금까지 모든 컬렉터는 reducing 팩토리 메서드로 정의할 수 있다. collect와 reduce 메서드는 무엇이 다른지 궁금할 것이다. 특히 이들 메서드로 
같은 기능을 구현할 수 있으므로 더욱 그러할 것이다.   
collect 메서드는 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계된 메서드인 반면 reduce는 두 값을 하나로 도출하는 불변형 연산이라는 점에서
의미론적인 문제가 일어난다.   
결국 컨테이너 관련 작업이면서 병렬성을 확보하려면 collect 메서드로 리듀싱 연산을 구현하는 것이 바람직이다. 

5장에서는 컬렉터를 이용하지않고도 다른 방법(요리 스트림을 요리의 칼로리로 매핑한 다음에 이전 버전의 에제에서 사용한
메서드 참조로 결과 스트림을 리듀싱)으로 같은 연산을 할 수 있음을 살펴봤다.
```groovy
 //컬렉터를 사용하지 않고 sum 계산, 널문제를 피하기 위해, get 을 사용
Integer reduceSum = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
System.out.println("reduceSum = " + reduceSum);

```
한 개의 인수를 갖는 reduce를 스트림에 적용한 다을 예제와 마찬가지로 reduce도 빈 스트림과 관련한 널 문제를 피할 수 있도록 int가 아닌
Optional<Integer>를 반환한다. 그리고 get으로 Optional 객체 내부의 값을 추출했다. 요리 스트림은 비어있지 않다는 사실을 알고 있으므로
get을 자유롭게 사용할 수 있다. 스트림을 IntStream으로 매핑한 다음에 sum 메서드를 호출하는 방법으로도 결과를 얻을 수 있다.

```groovy
//mapToInt사용
int sum = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
System.out.println("sum = " + sum);
```

### 자신의 상황에 맞는 최적의 해법 선택
지금까지 살펴본 에제는 함수형 프로그래밍에서는 하나의 연산을 다양한 방법으로 해결할 수 있음을 보여준다. 또한 스트림 인터페이스에서 직접
제공하는 메서드를 이용하는 것에 비해 컬렉터를 이용하는 코드가 더 복잡하다는 사실도 보여준다. 코드가 좀 더 복잡한 대신 재사용성과 커스터마이즈
가능성을 제공하는 높은 수준의 추상화와 일반화를 얻을 수 있다. 
  
  문제를 해결할 수 있는 다양한 해결 방법을 확인한 다음에 가장 일반적으로 문제에 특화된 해결책을 고르는 것이 바람직하다. 이렇게 해서 가독성과 성능이라는
  두 마리 토끼를 잡을 수 있다. 예를 들어 메뉴의 전체 칼로릴르 계산하는 예제에서는(IntStream을 사용한) 가장 마지막에 확인한 해결 방법이
  가독성이 가장 좋고 간결하다. 또한 IntStream 덕분에 자동 언박싱 연산을 수행하거나 Integer를 int로 변환하는 과정 을 피할 수 있으므로 성능까지 좋다.

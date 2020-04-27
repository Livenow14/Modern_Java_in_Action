import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chapter5_8_BuildingStreams {
    public static void main(String[] args) throws IOException {

        // 스트림의 모든 문자열을 대문자로 변환한 후 문자열을 하나 씩 출력
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase)
                .forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();

        // Arrays.stream
        int[] numbers = { 2, 3, 5, 7, 11, 13 };
        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        System.out.print("Iterator : ");
        Stream.iterate(0,n->n+2)
                .limit(10)
                .forEach(t-> System.out.print(t + " "));


        //퀴즈 5.4 피보나치 수열
        System.out.println("피보나치 수열: ");
        Stream.iterate(new int[]{0,1}, t-> new int[]{t[1],t[0]+t[1]})
                .limit(20)
                .forEach(t-> System.out.print("("+t[0] + "," + t[1] + ") "));

        // Stream.generate를 이용한 임의의 double 스트림
        System.out.println("Generate로 랜덤 수 : ");
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        // Stream.generate을 이용한 요소 1을 갖는 스트림
        System.out.println("Generate로 1을 갖는 : ");
        IntStream.generate(() -> 1)
                .limit(5)
                .forEach(System.out::println);





        //어려워서 넘김..
        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {

            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int nextValue = previous + current;
                previous = current;
                current = nextValue;
                return previous;
            }

        };
        IntStream.generate(fib)
                .limit(10)
                .forEach(System.out::println);

/*        long uniqueWords = Files.lines(Paths.get("lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();

        System.out.println("There are " + uniqueWords + " unique words in data.txt");*/

    }
}

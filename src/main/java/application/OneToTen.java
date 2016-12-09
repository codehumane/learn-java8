package application;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * 1부터 10까지 출력하는 여러가지 코드
 */
@Slf4j
public class OneToTen {

    public static void simple() {
        Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (Integer n : iterable) {
            log.info(String.valueOf(n));
        }
    }

    public static void byStream() {
        Stream.iterate(1, x -> x + 1).limit(10).forEach(n -> log.info(String.valueOf(n)));
    }

    public static void byLambdaIterator() {
        Iterable<Integer> iterable = () ->
            new Iterator<Integer>() {

                private int i = 0;
                private static final int MAX = 10;

                public boolean hasNext() {
                    return i < MAX;
                }

                public Integer next() {
                    return i++;
                }
            };

        for (Integer n : iterable) {
            log.info(String.valueOf(n));
        }
    }
}

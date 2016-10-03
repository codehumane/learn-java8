package application;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chogh on 10/1/16.<br/>
 * java8 문법을 활용한 fizzbuzz 구현
 */
@Slf4j
public class FizzBuzz {

    public String convert(int number) {
//        Stream.iterate(1, x -> x + 1).limit(number) 으로 대체 가능
        String converted = IntStream.range(1, number + 1).
                mapToObj(x -> {
                    String str = "";
                    if (x % 3 == 0) str += "fizz";
                    if (x % 5 == 0) str += "buzz";
                    if (str.length() == 0) str += x;
                    return str;
                }).
                collect(Collectors.joining());

        if (converted.length() < 1) {
            throw new IllegalArgumentException();
        }

        return converted;
    }
}
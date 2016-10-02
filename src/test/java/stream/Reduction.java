package stream;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chogh on 10/1/16.<br/>
 * java8의 reduction 관련 학습
 *
 */
@Slf4j
public class Reduction {

    @Test
    public void sumByReduction() throws Exception {
        Optional<Integer> result = Stream.of(1, 2, 3, 5, 0, 7).
                reduce(Integer::sum);

        Assert.assertEquals(Optional.of(18), result);
    }

    @Test
    public void multiplicationByReduction() throws Exception {
        Optional<Integer> result = Stream.of(1, 2, 3, 5, 0, 7).
                reduce((x, y) -> x * y);

        Assert.assertEquals(Optional.of(0), result);
    }

    @Test
    public void filterReduced() throws Exception {
        Optional<Integer> result = Stream.of(1, 3).
                reduce((x, y) -> x * y).
                filter(x -> x % 2 == 0);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void mapReduced() throws Exception {
        Optional<String> result = Stream.of(new Data("gunhee"), new Data("cho")).
                reduce(Data::concat).
                map(Data::getName);

        Assert.assertEquals(Optional.of("gunheecho"), result);
    }

    @Test
    public void flatMapReduced() throws Exception {
        Optional<String> result = Stream.of(new Data("gunhee"), new Data("cho")).
                reduce(Data::concat).
                flatMap(Data::getNameOptional);

        // 지금처럼 flatMap을 사용하는 것은 map의 사용에 비해 불편하지만,
        // 기존에 Optional을 반환하는 함수가 있다면 flatMap의 사용이 map보다 더 간편하다.

        Assert.assertEquals(Optional.of("gunheecho"), result);
    }

}

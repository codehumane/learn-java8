package application;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

/**
 * Created by chogh on 10/2/16.<br/>
 * FizzBuzz 테스트
 */
@Slf4j
public class FizzBuzzTest {

    @Test(expected = IllegalArgumentException.class)
    public void convert_0이하의_값은_예외_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(0);
    }

    @Test
    public void convert_1을_주면_1을_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(1);

        // Then
        Assert.assertEquals("1", result);
    }

    @Test
    public void convert_2를_주면_12를_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(2);

        // Then
        Assert.assertEquals("12", result);
    }

    @Test
    public void convert_3을_주면_12fizz를_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(3);

        // Then
        Assert.assertEquals("12fizz", result);
    }

    @Test
    public void convert_4를_주면_12fizz4를_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(4);

        // Then
        Assert.assertEquals("12fizz4", result);
    }

    @Test
    public void convert_5를_주면_12fizz4buzz를_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(5);

        // Then
        Assert.assertEquals("12fizz4buzz", result);
    }

    @Test
    public void convert_15를_주면_12fizz4buzzfizz78fizzbuzz11fizz1314fizzbuzz를_반환() throws Exception {
        // Given
        FizzBuzz fizzBuzz = new FizzBuzz();

        // When
        String result = fizzBuzz.convert(15);

        // Then
        Assert.assertEquals("12fizz4buzzfizz78fizzbuzz11fizz1314fizzbuzz", result);
    }

    @Test
    public void convert_큰_숫자에_대해서도_동작함을_보장() throws Exception {
        String result = new FizzBuzz().convert(1500000);
        assertTrue(result.startsWith("12fizz4buzzfizz78fizzbuzz11fizz1314fizzbuzz"));
    }
}
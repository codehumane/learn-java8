package application;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chogh on 12/9/16.
 */
public class OneToTenTest {

    @Test
    public void simple() throws Exception {
        OneToTen.simple();
    }

    @Test
    public void byStream() throws Exception {
        OneToTen.byStream();
    }

    @Test
    public void byLambdaIterator() throws Exception {
        OneToTen.byLambdaIterator();
    }

}
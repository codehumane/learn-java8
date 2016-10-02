package stream;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chogh on 10/1/16.<br/>
 * java8의 mapping 관련 학습
 */
@Slf4j
public class Map {

    @Test
    public void countLine() throws Exception {
        // setup
        Path path = Files.createFile(Paths.get("src/test/resources/word.txt"));
        BufferedWriter writer = Files.newBufferedWriter(path);
        writer.write("Hello\n");
        writer.write("world\n");
        writer.write("\n");
        writer.write("Gunhee\n");
        writer.write("Cho\n");
        writer.write(".");
        writer.close();

        // do
        long count = Files.lines(path).count();

        // tear down
        Assert.assertEquals(6, count);
        Files.delete(path);
    }

    @Test
    public void mapToUpperCase() throws Exception {
        // setup
        String contents = "Hi\nGunhee.";

        // do
        String mapped = Stream.of(contents.split("\n")).
                map(String::toUpperCase).
                collect(Collectors.joining("\n"));

        // tear down
        Assert.assertEquals("HI\nGUNHEE.", mapped);
    }

    @Test
    public void flatMapToUpperCase() throws Exception {
        // setup
        String contents = "Hi\nGunhee.";

        // do
        String mapped = Stream.of(contents.split("\n")).
                flatMap(line -> Stream.of(line.split(""))).
                map(String::toUpperCase).
                collect(Collectors.joining("\n"));

        // tear down
        Assert.assertEquals("HI\nG\nU\nN\nH\nE\nE\n.", mapped);
    }
}
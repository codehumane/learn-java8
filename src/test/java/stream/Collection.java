package stream;

import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chogh on 10/2/16.<br/>
 * java8 collection 학습
 */
public class Collection {

    @Test
    public void collectToHashSet() throws Exception {
        HashSet<String> set = Stream.of("Hello", "World", "I'm", "Gunhee").
                collect(HashSet::new, HashSet::add, HashSet::addAll);

        Assert.assertEquals(4, set.size());
        Assert.assertTrue(set.contains("I'm"));
        Assert.assertTrue(set.contains("Gunhee"));
        Assert.assertTrue(set.contains("Hello"));
        Assert.assertTrue(set.contains("World"));
    }

    @Test
    public void collectToTreeSet() throws Exception {
        TreeSet<String> set = Stream.of("Hello", "World", "I'm", "Gunhee").
                collect(TreeSet::new, TreeSet::add, TreeSet::addAll);

        Assert.assertEquals(4, set.size());

        Iterator<String> iterator = set.iterator();
        Assert.assertEquals("Gunhee", iterator.next());
        Assert.assertEquals("Hello", iterator.next());
        Assert.assertEquals("I'm", iterator.next());
        Assert.assertEquals("World", iterator.next());
    }

    @Test
    public void collectToTreeSetWithCollectors() throws Exception {
        TreeSet<String> set = Stream.of("Hello", "World", "I'm", "Gunhee").
                collect(Collectors.toCollection(TreeSet::new));

        Assert.assertEquals(4, set.size());

        Iterator<String> iterator = set.iterator();
        Assert.assertEquals("Gunhee", iterator.next());
        Assert.assertEquals("Hello", iterator.next());
        Assert.assertEquals("I'm", iterator.next());
        Assert.assertEquals("World", iterator.next());
    }

    @Test
    public void collectWithJoining() throws Exception {
        String joined = Stream.of("Hello", "World", "I'm", "Gunhee").
                collect(Collectors.joining(" "));

        Assert.assertEquals("Hello World I'm Gunhee", joined);
    }

    @Test
    public void collectWithIntSummaryStatistic() throws Exception {
        IntSummaryStatistics stat = Stream.of("Hello", "World", "I'm", "Gunhee").
                collect(Collectors.summarizingInt(String::length));

        Assert.assertEquals(4.75, stat.getAverage(), 1e-15);
        Assert.assertEquals(4, stat.getCount());
        Assert.assertEquals(19, stat.getSum());
        Assert.assertEquals(6, stat.getMax());
    }
}

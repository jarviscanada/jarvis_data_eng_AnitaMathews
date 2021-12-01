package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class LambdaStreamImpTests {
    LambdaStreamImp lsi = new LambdaStreamImp();
    String[] strings = {"a", "b", "c"};
    int[] ints = {1, 2, 3, 4, 5};

    @Test
    public void createStrStreamTest() {
        assertTrue("String stream should not be null", (lsi.createStrStream(strings)) != null);
    }

    @Test
    public void toUpperCaseTest() {
        Stream<String> strStream = lsi.toUpperCase(strings);
        String[] output = {"A", "B", "C"};
        assertArrayEquals("Not converting to uppercase", strStream.toArray(), output);
    }

    @Test
    public void filterTest() {
        Stream<String> stringStream = lsi.filter(lsi.createStrStream(strings), "a");
        String[] output = {"b", "c"};
        assertArrayEquals("Not removing strings with matching pattern", stringStream.toArray(), output);
    }

    @Test
    public void createIntStreamTest() {
        assertTrue("Int stream should not be null", (lsi.createIntStream(ints)) != null);
    }

    @Test
    public void createIntStreamRangeTest() {
        IntStream intStream = lsi.createIntStream(1, 5);
        assertArrayEquals("IntStream range not working", intStream.toArray(), ints);
    }

    @Test
    public void squareRootTest() {
        double[] sqrtOutput = {2, 3, 4};
        int[] sqrtInput = {4, 9, 16};
        IntStream stream = lsi.createIntStream(sqrtInput);
        assertArrayEquals("Not finding the square root properly", lsi.squareRootIntStream(stream).toArray(), sqrtOutput, 0.001);
    }

    @Test
    public void printMessagesTest() {
        lsi.printMessages(strings, lsi.getLambdaPrinter("msg:", "!") );
        //should print:
        //msg:a!
        //msg:b!
        //msg:c!
    }

    @Test
    public void printOddTest() {
        lsi.printOdd(lsi.createIntStream(ints), lsi.getLambdaPrinter("odd number:", "!"));
        //should print:
        //odd number:1!
        //odd number:3!
        //odd number:5!
    }

    @Test
    public void flatNestedIntTest() {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> elem1 = new ArrayList<>();
        List<Integer> elem2 = new ArrayList<>();
        elem1.add(0);
        elem1.add(3);
        elem2.add(2);
        elem2.add(4);
        elem2.add(5);
        list.add(elem1);
        list.add(elem2);

        int[] squareOutput = {0, 9, 4, 16, 25};

        Stream<Integer> flatNestStream = lsi.flatNestedInt(list.stream());
        //flatNestStream.forEach(s -> System.out.println(s));
        assertArrayEquals("Not flattening list of integers and squaring", flatNestStream.mapToInt(Integer::new).toArray(), squareOutput);
    }
}

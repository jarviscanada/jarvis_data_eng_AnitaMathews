package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamImp implements LambdaStreamExc {

    public Stream<String> createStrStream(String[] strings) {
        Stream<String> stream = Stream.of(strings);
        return stream;
    }

    public Stream<String> toUpperCase(String[] strings) {
        Stream<String> stream = createStrStream(strings).map(element -> element.toUpperCase());
        return stream;
    }

    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        Stream<String> stream = stringStream.filter(element -> !(element.contains(pattern)));
        return stream;
    }

    public IntStream createIntStream(int[] arr) {
        IntStream intStream = IntStream.of(arr);
        return intStream;
    }

    public <E> List<E> toList(Stream<E> stream) {
        List<E> list = stream.collect(Collectors.toList());
        return list;
    }

    public List<Integer> toList(IntStream intStream) {
        List<Integer> list = intStream.boxed().collect(Collectors.toList());
        return list;
    }

    public IntStream createIntStream(int start, int end) {
        IntStream stream = IntStream.range(start, end);
        return stream;
    }

    public DoubleStream squareRootIntStream(IntStream intStream) {
        DoubleStream stream = intStream.asDoubleStream().map(element -> Math.sqrt(element));
        return stream;
    }

    public IntStream getOdd(IntStream intStream) {
        IntStream stream = intStream.filter(element -> element % 2 == 1);
        return stream;
    }

    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        Consumer<String> printer = s -> System.out.println(prefix + s + suffix);
        return printer;
    }

    public void printMessages(String[] messages, Consumer<String> printer) {
        createStrStream(messages).forEach(printer);
    }

    public void printOdd(IntStream intStream, Consumer<String> printer) {
        intStream.mapToObj(element -> Integer.toString(element)).forEach(printer);
    }

    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        //flatmap
        Stream<Integer> stream = ints.flatMap(Collection::stream).map(element -> element * element);
        return stream;

        //without flapmap
        //List<Integer> list = new ArrayList<>();
        //ints.forEach(list::addAll);
        //return list.stream().map(element -> element * element);
    }
}

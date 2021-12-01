package ca.jrvs.apps.practice;

import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {

    /**
     * Creates a String stream from an array
     *
     * @param strings
     * @return String stream
     */
    Stream<String> createStrStream(String[] strings);

    /**
     * Converts all strings to uppercase
     * @param strings
     * @return String stream
     */
    Stream<String> toUpperCase(String[] strings);

    /**
     * Filer strings that contain the specified pattern
     * @param stringStream
     * @param pattern
     * @return String stream
     */
    Stream<String> filter(Stream<String> stringStream, String pattern);

    /**
     * Creates an intStream from an int array
     * @param arr
     * @return int stream
     */
    IntStream createIntStream(int[] arr);

    /**
     * Convert a stream to a list
     * @param stream
     * @param <E>
     * @return List
     */
    <E> List<E> toList(Stream<E> stream);

    /**
     * Convert intStream to list
     * @param intStream
     * @return List of Integers
     */
    List<Integer> toList(IntStream intStream);

    /**
     * Create an IntStream given a range (start to end inclusive)
     * @param start
     * @param end
     * @return IntStream
     */
    IntStream createIntStream(int start, int end);

    /**
     * Convert intStream to doubleStream
     * Compute square root of each element
     * @param intStream
     * @return DoubleStream
     */
    DoubleStream squareRootIntStream(IntStream intStream);

    /**
     * Filter all even numbers and return odd numbers from an intStream
     * @param intStream
     * @return IntStream
     */
    IntStream getOdd(IntStream intStream);

    /**
     * Return a lambda function that prints a message with a prefix and suffix
     * e.g.
     * LambdaStreamExc lse = new LambdaStreamImp();
     * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
     * printer.accept("Message body");
     *
     * sout:
     * start>Message body<end
     *
     * @param prefix prefix str
     * @param suffix suffix str
     * @return Comsumer<String>
     */
    Consumer<String> getLambdaPrinter(String prefix, String suffix);

    /**
     * Print each message with a given printer
     * Please use `getLambdaPrinter` method
     *
     * e.g.
     * String[] messages = {"a","b", "c"};
     * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
     *
     * sout:
     * msg:a!
     * msg:b!
     * msg:c!
     *
     * @param messages
     * @param printer
     */
    void printMessages(String[] messages, Consumer<String> printer);

    /**
     * Print all odd number from a intStream.
     * Please use `createIntStream` and `getLambdaPrinter` methods
     *
     * e.g.
     * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
     *
     * sout:
     * odd number:1!
     * odd number:3!
     * odd number:5!
     *
     * @param intStream
     * @param printer
     */
    void printOdd(IntStream intStream, Consumer<String> printer);

    /**
     * Square each number from the input.
     * Please write two solutions and compare difference
     *   - using flatMap
     *
     * @param ints
     * @return
     */
    Stream<Integer> flatNestedInt(Stream<List<Integer>> ints);
}

package ca.jrvs.practice.algorithms;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class binarySearchImpTest extends TestCase {

    binarySearchImp binarySearchImp = new binarySearchImp();
    private Integer[] arr = {1,3,5,7,9};

    @Test
    public void testBinarySearchRecursion() {
        Optional<Integer> idx = binarySearchImp.binarySearchRecursion(arr, 5);
        assertEquals(Optional.of(2), idx);
    }

    @Test
    public void testBinarySearchRecursionEmpty() {
        Optional<Integer> idx = binarySearchImp.binarySearchRecursion(arr, 4);
        assertEquals(Optional.empty(), idx);
    }

    @Test
    public void testBinarySearchIteration() {
        Optional<Integer> idx = binarySearchImp.binarySearchIteration(arr, 7);
        assertEquals(Optional.of(3), idx);
    }

    @Test
    public void testBinarySearchIterationEmpty() {
        Optional<Integer> idx = binarySearchImp.binarySearchIteration(arr, 6);
        assertEquals(Optional.empty(), idx);
    }
}
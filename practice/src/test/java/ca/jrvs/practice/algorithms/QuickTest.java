package ca.jrvs.practice.algorithms;

import junit.framework.TestCase;

import static ca.jrvs.practice.algorithms.Quick.quickSort;
import static org.junit.Assert.assertArrayEquals;

public class QuickTest extends TestCase {

    public void testQuickSort() {
        int[] arr = {1,7,3,6,2,9};
        int[] arr_exp = {1,2,3,6,7,9};
        quickSort(arr);
        assertArrayEquals(arr, arr_exp);
    }
}
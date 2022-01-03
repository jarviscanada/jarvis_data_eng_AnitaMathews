package ca.jrvs.practice.algorithms;

import junit.framework.TestCase;

import static ca.jrvs.practice.algorithms.mergeSortImp.mergeSort;

public class mergeSortImpTest extends TestCase {

    public void testMergeSort() {
        int[] arr = {1,6,8,2,5,3,7,9,10,13,4,17,20,14};
        mergeSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
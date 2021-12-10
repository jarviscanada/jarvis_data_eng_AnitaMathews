package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

// write tests using JUnit
public class SwapTwoNumbers {
    public int[] swapNumbers(int[] arr) {
       // return swapBitwise(arr);
        return swapArithmetic(arr);
    }

    /**
     * Big-O: O(1), bitwise operations are constant time
     * @param arr
     * @return
     */
    private int[] swapBitwise(int[] arr) {
        int a = arr[0];
        int b = arr[1];

        b = b^a;
        a = a^b;
        b = b^a;

        int[] swapped = new int[]{a, b};
        return swapped;
    }

    /**
     * Big-O: O(1) because of arithmetic operations
     * Integer overflow may occur with very large numbers
     * @param arr
     * @return
     */
    private int[] swapArithmetic(int[] arr) {
        int a = arr[0];
        int b = arr[1];

        a = a + b;
        b = a - b;
        a = a - b;

        int[] swapped = new int[]{a, b};
        return swapped;
    }

    public static void main(String[] args) {
        SwapTwoNumbers stm = new SwapTwoNumbers();
        int[] arr = new int[] {3,2};
        System.out.println(Arrays.toString(stm.swapNumbers(arr)));
    }
}

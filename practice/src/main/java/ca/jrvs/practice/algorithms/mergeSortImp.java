package ca.jrvs.practice.algorithms;

import java.util.Arrays;

public class mergeSortImp {

    public static void mergeSort(int[] arr) {
        int n = arr.length;

        if (n < 2) {
            return;
        }

        int mid = n / 2;

        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, n);

        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int k = 0;

        int left_size = left.length;
        int right_size = right.length;

        while (i < left_size && j < right_size) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            }
            else {
                arr[k++] = right[j++];
            }
        }

        while (i < left_size) {
            arr[k++] = left[i++];
        }

        while (j < right_size) {
            arr[k++] = right[j++];
        }
    }
}

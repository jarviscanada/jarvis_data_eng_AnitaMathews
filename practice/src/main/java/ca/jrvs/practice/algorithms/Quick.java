package ca.jrvs.practice.algorithms;

public class Quick {

    public static void quickSort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int lo, int hi) {
        if (lo < hi) {
            //find partition index and place elements in correct location relative to pivot
            int idx = partition(arr, lo, hi);
            sort(arr, lo, idx-1);
            sort(arr, idx+1, hi);
        }
    }

    private static int partition(int[] arr, int lo, int hi) {
        int i = lo - 1;
        int pivot = arr[hi]; //pivot is the last element

        for (int j = lo; j < hi; j++){
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        //at this point, elements from idx 0 to i are less than the pivot
        //place pivot at correct location
        swap(arr, i+1, hi);
        return i+1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

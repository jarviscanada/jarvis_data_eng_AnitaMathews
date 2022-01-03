package ca.jrvs.practice.algorithms;

import java.util.Optional;

public class binarySearchImp {

    /**
     * find the the target index in a sorted array
     *
     * @param arr input array is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not found
     */
    public <E extends Comparable> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
        return binarySearchRecursive(arr, target, 0, arr.length - 1);
    }

    private <E extends Comparable> Optional<Integer> binarySearchRecursive(E[] arr, E target, int lo, int hi) {
        if (lo > hi) {
            return Optional.empty();
        }

        int midpoint = (lo + hi) / 2;

        if (target.compareTo(arr[midpoint]) == 0) {
            return Optional.of(midpoint);
        }

        else if (target.compareTo(arr[midpoint]) == 1) {
            lo = midpoint + 1;
            return binarySearchRecursive(arr, target, lo, hi);
        }

        else {
            hi = midpoint - 1;
            return binarySearchRecursive(arr, target, lo, hi);
        }

      }

    /**
     * find the the target index in a sorted array
     *
     * @param arr input array is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not found
     */
    public <E extends Comparable> Optional<Integer> binarySearchIteration(E[] arr, E target) {

        int lo = 0;
        int hi = arr.length - 1;

        while (lo <= hi) {
            int midpoint = (lo + hi) / 2;

            if (target.compareTo(arr[midpoint]) == 0) {
                return Optional.of(midpoint);
            }

            else if (target.compareTo(arr[midpoint]) == -1) {
                hi = midpoint - 1;
            }

            else {
                lo = midpoint + 1;
            }
        }
        return Optional.empty();
    }
}



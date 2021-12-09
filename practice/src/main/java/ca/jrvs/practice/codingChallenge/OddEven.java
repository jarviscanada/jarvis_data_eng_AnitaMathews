package ca.jrvs.practice.codingChallenge;

public class OddEven {

    /**
     * Big-O: O(1) because it is an arithmetic operation
     */

    public String oddEvenMod(int i) {
        return i % 2 == 0 ? "even" : "odd";
    }

    /**
     * Big-O:
     * Check the last bit of the number
     * If it is set - number is odd, if not then it is even.
     * e.g. 1011 & 0001 ==> 0001 (odd)
     */

    public String oddEvenBit(int i) {
        return ((i & 1) == 0) ? "even" : "odd";
    }
}

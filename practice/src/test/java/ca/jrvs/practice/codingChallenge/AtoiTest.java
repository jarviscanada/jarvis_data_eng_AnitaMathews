package ca.jrvs.practice.codingChallenge;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//add more tests
public class AtoiTest {
    Atoi atoi = new Atoi();

    @Test
    public void myAtoiTest() {
        assertEquals(42, atoi.myAtoi("42"));
    }
}

package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// works but could be simplified - redo this.
public class Atoi {

    public int myAtoi(String s) {
        String strTrim = s.trim();
        if (strTrim.length() == 0) {return 0;}
        boolean positive = true;
        int i = 0;
        List<String> strIntArr = new ArrayList<String>();
        Character firstChar = strTrim.charAt(0);

        if (firstChar == '-') {
            positive = false;
            strIntArr.add(Character.toString(firstChar));
            i = 1;
        }

        if (firstChar == '+') {
            strIntArr.add(Character.toString(firstChar));
            i = 1;
        }

        String strTrimtwo = strTrim.substring(i).replaceFirst("^0*", "");
        int j = 0;
        // loop through the string while each value is a number
        while (j < strTrimtwo.length() && Character.isDigit(strTrimtwo.charAt(j))) {
            //store in String array
            strIntArr.add(Character.toString(strTrimtwo.charAt(j)));
            j++;
        }

        if (strIntArr.size() == 0 || (strIntArr.size() == 1 && (strIntArr.contains("-") || strIntArr.contains("+")))) { return 0; }

        String strInt = strIntArr.stream().collect(Collectors.joining());

        // check overflow or underflow
        boolean large;
        boolean small;

        if (strInt.charAt(0) == '+' || strInt.charAt(0) == '-') {
            large = strInt.length() - 1 > Integer.toString(Integer.MAX_VALUE).length() && positive;
            small = strInt.length() - 1 > Integer.toString(Integer.MIN_VALUE).length() && !(positive);
        }

        else {
            large = strInt.length() > Integer.toString(Integer.MAX_VALUE).length() && positive;
            small = strInt.length() > Integer.toString(Integer.MIN_VALUE).length() && !(positive);
        }

        int val;

        if (large) {
            val = (int)(Math.pow(2, 31) - 1);
        }
        else if (small) {
            val = (int)Math.pow(-2, 31);
        }
        else {
            long longVal = Long.parseLong(strInt);
            if (longVal > Integer.MAX_VALUE) {
                val = (int)(Math.pow(2, 31) - 1);
            }
            else if (longVal < Integer.MIN_VALUE) {
                val = (int)Math.pow(-2, 31);
            }
            else {
                val = Integer.parseInt(strInt);
            }
        }

        // return final result
        return (int)val;
    }

}

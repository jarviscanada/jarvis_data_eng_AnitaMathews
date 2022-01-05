package ca.jrvs.apps.practice;
import org.junit.Test;

import java.util.regex.*;
import static org.junit.Assert.assertTrue;

public class RegexExcImp implements RegexExc {

    public boolean matchJpeg(String filename) {
        Pattern pattern = Pattern.compile("[\\w-]+\\.jpg$|[\\w-]+\\.jpeg$");
        Matcher matcher = pattern.matcher(filename);
        return matcher.find();
    }


    public boolean matchIp(String ip) {
        Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        Matcher matcher = pattern.matcher(ip);
        return matcher.find();
    }


    public boolean isEmptyLine(String line) {
        Pattern pattern = Pattern.compile("^\\s*$");
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Test
    public void jpegTest(){
        RegexExcImp reg = new RegexExcImp();
        boolean match = reg.matchJpeg("test.jpeg");
        assertTrue("Not a jpg/jpeg file", match);
    }

    @Test
    public void ipTest(){
        RegexExcImp reg = new RegexExcImp();
        boolean match = reg.matchIp("190.16.0.1");
        assertTrue("Not an ip address", match);
    }

    @Test
    public void isEmptyLineTest(){
        RegexExcImp reg = new RegexExcImp();
        boolean match = reg.isEmptyLine("       ");
        assertTrue("Not an empty line", match);
    }

}

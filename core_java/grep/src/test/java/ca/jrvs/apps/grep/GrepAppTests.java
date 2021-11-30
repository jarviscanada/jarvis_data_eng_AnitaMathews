package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GrepAppTests {
    JavaGrepImp jgi = new JavaGrepImp();

    @Test
    public void listFilesTest() {
        List<File> fileList = jgi.listFiles("./");
        assertFalse("Not reading file directory properly.", fileList.isEmpty());
    }

    @Test
    public void readLinesTest() throws IOException {
        List<String> lines = jgi.readLines(new File("./data/txt/shakespeare.txt"));
        int FILESIZE = 124451; //from wc -l shakespeare.txt
        assertTrue("Did not read all lines from file.", lines.size() == 124456);
    }

    @Test
    public void containsPattern(){
        jgi.setRegex("dog");
        boolean match = jgi.containsPattern("There are cats, dogs and cows.");
        assertTrue("Pattern should have been found", match);
    }

    @Test
    public void writeFileTest() throws IOException {
        jgi.setOutFile("./out/writetest.txt");
        List<String> lines = new ArrayList<>();
        lines.add("apple");
        lines.add("box");
        lines.add("car");
        jgi.writeToFile(lines);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLinesExceptionTest() {
        //file does not exist
        jgi.readLines(new File("./data/txt/janeausten.txt"));
    }
}

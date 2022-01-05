package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JavaGrepLambdaImpTests {

    JavaGrepLambdaImp jgLambda = new JavaGrepLambdaImp();

    @Test
    public void listFilesTest() throws IOException {
        Stream<File> fileList = jgLambda.listFilesLambda("./");
        assertTrue("Not reading file directory properly.", fileList.findAny().isPresent());
    }

    @Test
    public void readLinesTest() {
        Stream<String> strStream = jgLambda.readLinesLambda(new File("./data/txt/shakespeare.txt"));
        int FILESIZE = 124456; //from wc -l shakespeare.txt
        assertTrue("Did not read all lines from file.", strStream.count() == 124456);
    }

    @Test
    public void writeFileTest() throws IOException {
        jgLambda.setOutFile("./out/writetestlambda.txt");
        String[] lines = {"apple", "box", "car"};
        Stream<String> strStream = Stream.of(lines);
        jgLambda.writeToFileLambda(strStream);
    }
}

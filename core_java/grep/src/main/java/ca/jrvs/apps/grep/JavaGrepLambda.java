package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface JavaGrepLambda {

    /**
     * Read a file and return a stream.
     * @param inputFile
     * @return String stream
     * @throws IOException
     */
    Stream<String> readLinesLambda(File inputFile) throws IOException;

    /**
     * Traverse a given directory recursively and return a stream
     * @param rootDir
     * @return File stream
     */
    Stream<File> listFilesLambda(String rootDir) throws IOException;

    /**
     * Write lines to a file
     * @param lines
     * @throws IOException
     */
    void writeToFileLambda(Stream<String> lines) throws IOException;
}

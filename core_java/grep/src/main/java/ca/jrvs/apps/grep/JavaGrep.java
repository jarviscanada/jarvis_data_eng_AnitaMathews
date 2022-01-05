package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * Process for high-level workflow
     * @throws IOException
     */

    void process() throws IOException;

    /**
     * Traverse a given directory recursively and return all files
     * @param rootDir root directory
     * @return all files under rootDir
     */

    List<File> listFiles(String rootDir);

    /**
     * Read a file and return all lines
     * (Explain FileReader, BufferedReader and character encoding)
     * @param inputFile file to be read
     * @return lines
     * @throws FileNotFoundException
     */

    List<String> readLines(File inputFile) throws IOException;

    /**
     * Check if line contains regex pattern specified by user
     * @param line input string
     * @return true if matches
     */

    boolean containsPattern(String line);

    /**
     * Write lines to a file
     * (Explore FileOutputStream, OutputStreamWriter and BufferedWriter)
     * @param lines matched lines
     * @throws IOException if write failed
     */

    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);

}

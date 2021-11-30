package ca.jrvs.apps.grep;

import ca.jrvs.apps.practice.RegexExcImp;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        List<File> listOfFiles = listFiles(rootPath);
        for (File f : listOfFiles) {
            for (String line : readLines(f)) {
                if (containsPattern(line)) matchedLines.add(line);
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {

        File folder = new File(rootDir);
        List<File> fileList = new ArrayList<>();
        return listFilesRecursively(folder, fileList);

    }

    private List<File> listFilesRecursively(File folder, List<File> fileList) {
        File[] currentFiles = folder.listFiles();
        if (currentFiles.length > 0) {
            for (File f : currentFiles) {
                if (f.isFile()) {
                    fileList.add(f);
                } else {
                    listFilesRecursively(f, fileList);
                }
            }
        }
        return fileList;
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        String curLine;
        List<String> setOfLines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            while ((curLine = br.readLine()) != null) {
                setOfLines.add(curLine);
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return setOfLines;
    }

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Test
    public void containsPattern(){
        JavaGrepImp jgi = new JavaGrepImp();
        jgi.setRegex("dog");
        boolean match = jgi.containsPattern("I like cats, dogs and cows.");
        assertTrue("Pattern should have been found", match);
        //System.out.println(fileList);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        FileWriter writer = new FileWriter(outFile);
        for (String s : lines) {
            writer.write(s + "\n");
        }
        writer.close();

    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    @Test
    public void listFilesTest(){
        JavaGrepImp jgi = new JavaGrepImp();
        List<File> fileList = jgi.listFiles("./");
        //System.out.println(fileList);
    }

    @Test
    public void readLinesTest() throws IOException {
        JavaGrepImp jgi = new JavaGrepImp();
        List<String> lines = jgi.readLines(new File("./data/txt/shakespeare.txt"));
        //System.out.println(lines);

    }

    @Test
    public void writeFileTest() throws IOException {
        JavaGrepImp jgi = new JavaGrepImp();
        jgi.setOutFile("./out/writetest.txt");
        List<String> lines = new ArrayList<>();
        lines.add("apple");
        lines.add("box");
        lines.add("car");
        jgi.writeToFile(lines);
        //System.out.println(fileList);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
        }

        //default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }
}

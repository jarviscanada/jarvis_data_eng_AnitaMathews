package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

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
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        String curLine;
        List<String> setOfLines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            while ((curLine = br.readLine()) != null) {
                setOfLines.add(curLine);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File does not exist or cannot be read.");
        }
        return setOfLines;
    }

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
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

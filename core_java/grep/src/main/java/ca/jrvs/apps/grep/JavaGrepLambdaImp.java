package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp implements JavaGrepLambda {

    @Override
    public void process() throws IOException {
        Stream<File> files = listFilesLambda(getRootPath());
        files.forEach(f -> {
            Stream<String> strStream = readLinesLambda(f);
            try {
                writeToFileLambda(strStream.filter(line -> containsPattern(line)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    @Override
    public Stream<String> readLinesLambda(File inputFile) throws IllegalArgumentException {
        Stream<String> strStream;
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            strStream = br.lines();
        } catch (IOException e) {
            throw new IllegalArgumentException("File does not exist or cannot be read.");
        }
        return strStream;
    }

    @Override
    public Stream<File> listFilesLambda(String rootDir) throws IOException {
        Stream<File> fileStream = Files.walk(Paths.get(rootDir)).filter(Files::isRegularFile).map(element -> element.toFile());
        return fileStream;
    }

    @Override
    public void writeToFileLambda(Stream<String> lines) throws IOException {
        FileWriter file = new FileWriter(getOutFile());
        BufferedWriter bw = new BufferedWriter(file);
        lines.forEach(element -> {
            try {
                bw.write(element + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw.close();
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
        }
    }
}



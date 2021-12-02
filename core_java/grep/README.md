# Introduction

The purpose of this application is to mimic the Linux `grep` command. The app searches for a Regex pattern in a set of files in a given directory and writes any matching lines into a specified output text file. This is accomplished by first obtaining a list of files in the given directory, reading each line of each file and determining if a match is found. It was written using Java and also makes use of the `slf4j` and `JUnit` libraries for logging and unit tests, respectively. The Lambda and Stream APIs were also used in order to optimize memory usage. The app was packaged using Maven and saved to Docker Hub as a Docker Image where it can be pulled and run.

# Quick Start
Below are some quick start commands to run the application using the Docker image on Docker Hub.
```
regex_pattern=".*Romeo.*Juliet.*"
src_dir="./data"    # contains shakespeare.txt
outfile=grep_$(date +%F_%T).txt

docker run --rm -v `pwd`/data:/data -v `pwd`/out:/out armathews/grep ${regex_pattern} ${src_dir} /out/${outfile}

# matched lines will be recorded in outfile
cat out/$outfile
```

# Implementation
This project was implemented using Java where the application takes a Regex pattern, a directory and an outfile as command line arguments.
The pseudocode outlining the process of finding and saving the matched lines to an output file can be seen in the next section.

## Pseudocode
```
matchedLines = []
# list files recursively in specified directory
for file in listFiles(rootDir):
    for line in readLines(file):
        # check if line contains pattern (given as cmd line argument)
        if containsPattern(line):
            matchedLines.add(Line)
            
# write matched lines to output file
writeToFile(matchedLines)
```

## Performance Issue
A `java.lang.OutOfMemoryError` occurs if there is not enough memory in the heap for the application to run.
For example, if the max heap size is set to 5MB, this is not enough to read in the `shakespeare.txt` test file as this file
is greater than 5MB. One solution is to increase the max heap size to ensure that it is greater than the test file size.
Another solution is to use Streams as opposed to Lists for the reading/writing/listing files methods.
While Lists are stored entirely in memory, only the items being modified are in memory when using Streams. 
This allows the application to process large amounts of data with a small heap memory.

# Test
The application was tested by unit testing individual methods and comparing the final output of the application to the expected output. 

## Unit Tests
Several unit tests were performed by comparing the result to the expected output of that particular method. Examples can be seen below:

```
// Testing methods in JavaGrepImp

    JavaGrepImp jgi = new JavaGrepImp();

    @Test
    public void readLinesTest() throws IOException {
        List<String> lines = jgi.readLines(new File("./data/txt/shakespeare.txt"));
        int FILESIZE = 124451; //expected value obtained from "wc -l shakespeare.txt"
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
        jgi.writeToFile(lines); //manual check to see if lines were written properly
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLinesExceptionTest() {
        //file does not exist
        jgi.readLines(new File("./data/txt/janeausten.txt"));
    }
    
// Testing methods in JavaGrepLambdaImp (differs due to Stream return types in read/write/list files methods)
    
    JavaGrepLambdaImp jgLambda = new JavaGrepLambdaImp();
    
    @Test
    public void readLinesTest() {
        Stream<String> strStream = jgLambda.readLinesLambda(new File("./data/txt/shakespeare.txt"));
        int FILESIZE = 124451; //from wc -l shakespeare.txt
        assertTrue("Did not read all lines from file.", strStream.count() == 124456);
    }

    @Test
    public void writeFileTest() throws IOException {
        jgLambda.setOutFile("./out/writetestlambda.txt");
        String[] lines = {"apple", "box", "car"};
        Stream<String> strStream = Stream.of(lines);
        jgLambda.writeToFileLambda(strStream); //manual check to see if lines writen correctly
    }

```
All tests exited with exit code 0 indicating that the methods were performing as expected.

## Testing Overall Output

### Single File
The expected output of the app is equivalent to that obtained using the `grep` command-line command. Using a test Regex pattern and the `shakespeare.txt` file, the expected output can be seen below:
```
> regex_pattern=".*Romeo.*Juliet.*"
> src_dir="./data"
> egrep -r ${regex_pattern} ${src_dir}

# output
    Is father, mother, Tybalt, Romeo, Juliet,
Enter Romeo and Juliet aloft, at the Window.
    And Romeo dead; and Juliet, dead before,
    Romeo, there dead, was husband to that Juliet;
```

The output from the application using the same parameters is:
```
> java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt
> cat ./out/grep.txt

# output
    Is father, mother, Tybalt, Romeo, Juliet,
Enter Romeo and Juliet aloft, at the Window.
    And Romeo dead; and Juliet, dead before,
    Romeo, there dead, was husband to that Juliet;
```

As seen, the output from the app matches the expected output.

### Multiple Files
The `shakespeare.txt` was duplicated in the same folder to test whether matching lines from multiple files were being recorded. The output for this test case can be seen below:

```
# data folder contains two copies of shakespeare.txt file
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt
cat ./out/grep.txt

# output
    Is father, mother, Tybalt, Romeo, Juliet,
Enter Romeo and Juliet aloft, at the Window.
    And Romeo dead; and Juliet, dead before,
    Romeo, there dead, was husband to that Juliet;
    Is father, mother, Tybalt, Romeo, Juliet,
Enter Romeo and Juliet aloft, at the Window.
    And Romeo dead; and Juliet, dead before,
    Romeo, there dead, was husband to that Juliet;
```

The output matches the expected result.

# Deployment
This application was deployed by building a local Docker image and pushing it to Docker Hub.
First, a DockerFile was created which contains the entrypoint of the application.
The application was then packaged using Maven. A local Docker image was built which was then pushed to Docker Hub after running a Docker container with the arguments to be passed to the program.
The main steps can be seen below:
```
cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF

mvn clean package
docker build -t armathews/grep .
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log armathews/grep .*Romeo.*Juliet.* /data /log/grep.out
docker push armathews/grep
```

# Improvements
Three improvements that could be made include:
* Implementing only one interface which would include all needed methods
* Record filenames where pattern matches are found
* Allow options for multiple regex patterns/outfiles


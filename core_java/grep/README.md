# Introduction
(50-100 words)
Discuss the design of each app. What does the app do? What technologies have you used? (e.g. core java, libraries, lambda, IDE, docker, etc..)

# Quick Start
How to use your apps? 

#Implementation
This project was implemented using Java where the application takes a Regex pattern, a directory and an outfile as command line arguments.
The pseudocode outlining the process of finding and saving the matched lines to an output file can be seen in the next section.
## Pseudocode
```
matchedLines = []
//list files recursively in specified directory
for file in listFiles(rootDir):
    for line in readLines(file):
        //check if line contains pattern (given as cmd line argument)
        if containsPattern(line):
            matchedLines.add(Line)
            
//write matched lines to output file
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
How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)

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


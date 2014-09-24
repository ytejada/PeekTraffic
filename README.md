PeekTraffic
===========

Solution for the problem Peek traffic puzzle proposed in www.codeeval.com


# Building the project

    The project can be built using gradle. Within the root directory (from now on $projectDir) of the project the gradlew executable file it's located which cna be used as an standalone gradle executable for building the project.

# Build an executable Jar:
    To generate an executable jar which executes the built program execute:
      gradlew jar
    This should generate a directory called "build" in the root folder of the project. The jar file it's under the location "build/libs/", and can be executed as follows:
      java -jar $projectDir/build/libs/ChallengeBackLane-1.0.jar $projectDir/input.txt   (note that the first argument it's the file containing the data)
# Executing Tests
    To run unit tests run the following command:
      gradlew test
    This will create a new folder which will contain all the information about the unit test execution.The overall execution results are located in the file:
      $projectDir/build/reports/tests/index.html
    
# Creating code coverage report
    For creating a code coverage report run the next command:
      gradlew test jacocoTestReport
    This will create an html file which will contain the coverage information. This file is located in:
      $projectDir/build/jacocoHtml/index.html
  

# Roomba-Robot
A Roomba robot that clean a space following a command sequence

## Prerequisites
This project is write in Java and build by gradle, so you need at least to have them installed

- [Java 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [gradle 3.4 or higher](https://gradle.org/releases/)

## Setup
Clone or download the project from github

### Build the project
Once you have the project. Open your preferred command line tool make sure locate in the root folder of the project.  
Then write: `gradle build` and press enter. 
After finish, you should see a `BUILD SUCCESSFUL` message on the terminal.  
When build finish the artifact and the coverage report are created.

### `.Jar` file and running the project.
You can find the `.Jar` file in the folder `build\libs` with the name `Roomba-robot.jar`
1. First of all, you need to have an Instructions file with the following structure [test.json](https://github.com/shenlkm/Roomba-Robot/blob/master/res/test.json) where:
   - **Map** is the representation of the space to clean where:
     - `"C"` and `"null"` represent cleaned, space or obstacles and `"S"` a dirty space.
   - **Start** is the initial point with `x` and `y` coordinates and a compass `facing` as one of the four cardinal points `[N,E,S,O]`
   - **commands** is the list of command the robot will follow up where:
     - `{TL: turn left, TR: turn right, A: advance, C: clean}` 
   - **battery** is the amount of battery remaining in the robot.  
2. In order to run this project in the terminal `$java -jar "Roomba-robot.jar" <source.json> <result.json>` where:
   - `<source.json>` is the file with the structure above
   - `<result.json>` is the file where the final state of the robot will be written.

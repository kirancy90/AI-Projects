ARTIFICIAL INTELLIGENCE FALL 2015 ASSIGNMENT #3
CSP AND MRV

INSTALLATION
————————————
The program has been written in JAVA. The version of java on which it works correctly:
java version "1.8.0_60"
Please install this version or higher before running the code.

COMPILATION AND EXECUTION
—————————————————————————
The files needed to run this program are:

JobsPuzzle.java (To run the first puzzle that is Jobs assignment Puzzle)
EinsteinPuzzle.java (To run the second puzzle that is Einstein’s Zebra Puzzle)

In order to compile the java code, Please navigate to directory that has these files in terminal. Then run the command:
javac JobsPuzzle.java
javac EinsteinPuzzle.java

This compiles the code and creates class files.
In order to execute it run the command in the same directory as given below

java JobsPuzzle
This will run jobs puzzle for both CSP and MRV algorithms and gives result for both. It also prints the number of States visited.
java EinsteinPuzzle

This will run Einstein’s puzzle for both CSP and MRV algorithms and gives result for both. It also prints number of states visited for both.

For both the problems I am shuffling the order in which the domain is arranged each time. So it will give different states each time for both CSP and MRV. 

OUPUT
—————
The output displayed in the terminal includes:
Jobs Puzzle:
The assignment of Jobs to different people by applying CSP algorithm
The number of states visited by CSP algorithm
The assignment of jobs to different people by applying MRV algorithm
The number of states visited by MRV algorithm
Einstein’s Puzzle:
The assignment to different Houses with nationality, color, animal, drinks and eats by applying CSP algorithm
The number of states visited by CSP algorithm
The assignment to different Houses with nationality, color, animal, drinks and eats by applying MRV algorithm
The number of states visited by MRV algorithm


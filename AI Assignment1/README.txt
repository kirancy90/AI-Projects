ARTIFICIAL INTELLIGENCE FALL 2015 ASSIGNMENT #1
PATH NAVIGATION AND COMPARISON

INSTALLATION
————————————
The program has been written in JAVA. The version of java on which it works correctly:
java version "1.8.0_60"
Please install this version or higher before running the code.

COMPILATION AND EXECUTION
—————————————————————————
The files needed to run this program are:

Assignment1.java  (Source Code)
ATM.graph.txt   (Graph File)

In order to compile the java code, please make sure to keep both “Assignment1.java” and the graph input file in the same directory. In the terminal navigate to that directory which has both these files. Then run the command:

javac Assignment1.java

This compiles the code and creates class files.
In order to execute it run the command in the same directory as given below

java Assignment1 arg[0] arg[1] arg[2] arg[3] arg[4] arg[5]

arg[0] -> Search Type (BFS, DFS, GBFS)
arg[1] -> File Name of graph
arg[2] -> X coordinate of initial vertex
arg[3] -> Y coordinate of initial vertex
arg[4] -> X coordinate of goal vertex
arg[5] -> Y coordinate of goal vertex

Example: java Assignment1 BFS ATM.graph.txt 1 1 4 4

OUPUT
—————
The output displayed in the terminal includes:
1. Trace of the final path found (coordinates of vertices in the path)
2. Search Algorithm used
3. Total number of iterations performed
4. Maximum size of frontier
5. Total number of vertices visited
6. Length of the final path

The remaining diagnostic transcripts are printed in a file named “diagnostic.txt” which will be created in the same directory as the source code. This file contains the information in each look like iteration number, frontier size, popped node, depth of node, distance from goal etc.

FORMAT OF “diagnostic.txt” file
———————————————————————————————
It contains the problem information on top 
Search Type, Source vertex, Goal vertex
Then it contains the transcripts about the way the algorithm is working

ASSUMPTIONS
———————————
I have assumed that the input provided in the command line is in the same way as mentioned in this file. If it is not in that format the program might give error and close.
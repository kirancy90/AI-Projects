ARTIFICIAL INTELLIGENCE FALL 2015 ASSIGNMENT #2
BLOCKS WORLD PROBLEM
NAME: KIRAN CHANAMALLAPPA YALASANGI
UIN: 324006395

INSTALLATION
————————————
The program has been written in JAVA. The version of java on which it works correctly:
java version "1.8.0_60"
Please install this version or higher before running the code.

COMPILATION AND EXECUTION
—————————————————————————
The files needed to run this program are:

BlocksWorld.java (Source Code)

In order to compile the java code, Please navigate to Assignment2.java directory in terminal. Then run the command:

javac BlocksWorld.java

This compiles the code and creates class files.
In order to execute it run the command in the same directory as given below

java BlocksWorld arg[0] arg[1]

arg[0] -> Number of Blocks
arg[1] -> Number of Stacks

Example: java BlocksWorld 5 3

OUPUT
—————
The output displayed in the terminal includes:

1. The initial Node (Problem generated)
2. The heuristic function used
3. Total number of goal tests performed
4. Maximum size of frontier
5. Depth of the goal node
6. The path of Solution. (From one state to another)

The remaining diagnostic transcripts are printed in a file named “trace_assignment2.txt” which will be created in the same directory as the source code. This file contains the information in each loop like iteration number, frontier size, g+h value which is Heuristic+Depth , depth of node etc.


FORMAT OF “trace_assignment2.txt” file
———————————————————————————————
It contains the initial state on top 
Then it contains the transcripts about the way the algorithm is working
Finally shows if it found the goal node or not with Depth, Number of Goal tests and Max Frontier size

ASSUMPTIONS
———————————
I have assumed that the input provided in the command line is in the same way as mentioned in this file. If it is not in that format the program might give error and close.


The example program traces for 5 Blocks and 3 Stacks is given below. The program finds results for all three Heuristics. It has a limit of 15000 goal tests beyond which it stops and gives no solution found. 

The initial State is: 
0 |  A 
1 |  D  E 
2 |  C  B 

These are the results for Custom Heuristic fucntion

Max Frontier : 14
Goal Tests: 6
Depth of Goal Node: 5
The Solution Path for Custom Heuristic Function is :

THe depth of node below is : 0
0 |  A 
1 |  D  E 
2 |  C  B 
THe depth of node below is : 1
0 |  A  B 
1 |  D  E 
2 |  C 
THe depth of node below is : 2
0 |  A  B  C 
1 |  D  E 
2 | 
THe depth of node below is : 3
0 |  A  B  C 
1 |  D 
2 |  E 
THe depth of node below is : 4
0 |  A  B  C  D 
1 | 
2 |  E 
THe depth of node below is : 5
0 |  A  B  C  D  E 
1 | 
2 | 
--------------------------------------------------

These are the results for Modified Out of Place Heuristic fucntion

Max Frontier : 14
Goal Tests: 6
Depth of Goal Node: 5
The Solution Path for Modified Out of Place Heuristic Fucntion is : 

THe depth of node below is : 0
0 |  A 
1 |  D  E 
2 |  C  B 
THe depth of node below is : 1
0 |  A  B 
1 |  D  E 
2 |  C 
THe depth of node below is : 2
0 |  A  B  C 
1 |  D  E 
2 | 
THe depth of node below is : 3
0 |  A  B  C 
1 |  D 
2 |  E 
THe depth of node below is : 4
0 |  A  B  C  D 
1 | 
2 |  E 
THe depth of node below is : 5
0 |  A  B  C  D  E 
1 | 
2 | 
--------------------------------------------------

These are the results for Normal Out of Place Heuristic fucntion

Max Frontier : 16
Goal Tests: 6
Depth of Goal Node: 5
The Solution Path for Normal Out of Place Heuristic Fucntion is : 

THe depth of node below is : 0
0 |  A 
1 |  D  E 
2 |  C  B 
THe depth of node below is : 1
0 |  A  B 
1 |  D  E 
2 |  C 
THe depth of node below is : 2
0 |  A  B  C 
1 |  D  E 
2 | 
THe depth of node below is : 3
0 |  A  B  C 
1 |  D 
2 |  E 
THe depth of node below is : 4
0 |  A  B  C  D 
1 | 
2 |  E 
THe depth of node below is : 5
0 |  A  B  C  D  E 
1 | 
2 | 

The trace is:

The Below trace is for Custom Heuristic function

Iteration Number= 1 Frontier Size= 0 f=g+h= -3 depth= 0
Iteration Number= 2 Frontier Size= 5 f=g+h= -4 depth= 1
Iteration Number= 3 Frontier Size= 8 f=g+h= -4 depth= 2
Iteration Number= 4 Frontier Size= 9 f=g+h= -4 depth= 3
Iteration Number= 5 Frontier Size= 12 f=g+h= -5 depth= 4

Success! Depth= 5 Total_Goal_Tests= 6 Max Frontier Size= 14

The Below trace is for Modified Out of Place Heuristic function

Iteration Number= 1 Frontier Size= 0 f=g+h= 8 depth= 0
Iteration Number= 2 Frontier Size= 5 f=g+h= 5 depth= 1
Iteration Number= 3 Frontier Size= 8 f=g+h= 2 depth= 2
Iteration Number= 4 Frontier Size= 9 f=g+h= 3 depth= 3
Iteration Number= 5 Frontier Size= 12 f=g+h= 0 depth= 4

Success! Depth= 5 Total_Goal_Tests= 6 Max Frontier Size= 14

The Below trace is for Normal Out of Place Heuristic function

Iteration Number= 1 Frontier Size= 0 f=g+h= 5 depth= 0
Iteration Number= 2 Frontier Size= 5 f=g+h= 5 depth= 1
Iteration Number= 3 Frontier Size= 8 f=g+h= 5 depth= 2
Iteration Number= 4 Frontier Size= 9 f=g+h= 6 depth= 3
Iteration Number= 5 Frontier Size= 13 f=g+h= 6 depth= 4

Success! Depth= 5 Total_Goal_Tests= 6 Max Frontier Size= 16
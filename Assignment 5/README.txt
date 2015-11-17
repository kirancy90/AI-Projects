Artificial Intelligence, Fall 2015
Programming Assignment #5 : Propositional Satisfiability Solver (DPLL)

Author : Kiran Chanamallappa Yalasangi (UIN : 324006395)

Installation
------------
This code is been written using Python 2.7.10 on Mac, using Eclipse IDE (It can run on command prompt (Terminal) as well).

Python 2.7.10 comes preinstalled on most Linux distributions and MacBooks too. Please ensure version 2.7.10 if you are running on windows too. 

Files Included
------------
This archive has 5 files (including this file) - dpll.py (The code file to run), example1.kb (KB for abstract Boolean problem), agent_jobs.kb (KB for multi-task agent assignment problem), Table and Transcripts.pdf(Contains the table of statistics and transcripts for 3 problems) 
 

Running the DPLL
------------------------
To execute the program for DPLL, execute this command from a bash window, or from Windows Powershell.

python dpll.py agent_jobs.kb “painter sander gluer joiner” T T

This command will run DPLL for multi-agent task-assignment problem from the input file agent_jobs.kb which is in CNF form. The 2 arguments in the end give the toggle for Pure symbol and Unit clause heuristics respectively. In this case both are true.

You can also run for the abstract Boolean problem given in Question by using  “example1.kb” file as the first argument.

The general format of the command to be run is:

python dpll.py arg1 arg2 arg3 arg4

where:
arg1 = Name of the input file in which the CNF for of the clauses is present
arg2(Optional) = Input list of Job requirements in Quotes (Ex: “painter joiner”)
arg3 = Toggle for Pure symbol heuristic (T = True, F = False)
arg4 = Toggle for Unit clause heuristic (T = True, F = False)

The format of the input file is as mentioned in the question. So you can use # to comment in input files.


Ouput for DPLL:
-----------------------

The output trace is printed in the terminal itself. The output will terminate and give the steps it processed to generate the truth assignment for each proposition. 


Format of output is:

1. Input taken as CNF form
2. All Propositions
3. Successive Iterations and which proposition is set to what truth value
4. If a truth assignment is found which satisfies all clauses the Program terminates
5. Total Iteration number is printed as Nodes Searched
6. The solution is printed which is truth value for each proposition
7. The agents needed to be sent is printed



ASSUMPTIONS
———————————
I have assumed that the input provided in the command line is in the same way as mentioned in this file. If it is not in that format the program might give error and close.


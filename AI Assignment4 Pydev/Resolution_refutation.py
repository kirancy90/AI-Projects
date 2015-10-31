from sys import argv
import sys
import copy
from __builtin__ import list
try:
    import Queue as Q  
except ImportError:
    import queue as Q
'''
Created on Oct 25, 2015

@author: kiranyalasangi
'''
globalvar = 0
lineNum = 0
maxQueue=0
class Clause:
    def __init__(self):
        self.clause_set= set()
        self.clause_list = []
        self.parent1 = -1
        self.parent2 = -1
        self.length = 0
        self.index = -1
        self.string = ""
    
    def getList(self):
        temp=""
        for p in self.clause_list:
            temp = temp + p+" v "
        if len(temp) > 3:
            temp=temp[:-3]
        if temp == "":
            return "[]"
        
        return temp
        
        
   
    
class ResClauses:
    def __init__(self,clause1,clause2):
        self.clause1 = clause1
        self.clause2 = clause2
        self.tot_length = self.clause1.length+self.clause2.length;
    def __cmp__(self,other):
        return cmp(self.tot_length,other.tot_length)

def main():
    filename = argv[1]
    file = open(filename, 'r')
    out_file = open("output_resolution.txt",'w')
    clauses = []
    length = 0
    global lineNum
    for line in file:
        if line.strip() and not line[0] == "#":
            sys.stdout.write("%s: %s" %(lineNum, line))
            lineNum+=1
            words = line.split()
            temp = Clause()
            length = 0
            for word in words:  
                temp.clause_set.add(word)
                length+=1
            temp.clause_list=sorted(temp.clause_set)
            temp.length=length
            global globalvar
            temp.index = globalvar
            globalvar+=1
            clauses.append(temp)
    
    print ""
    ans = resolution(clauses,out_file)
    if isinstance(ans, Clause):
        printResult(clauses, ans,0)
    else:
        print "This is not an entailment so no Empty clause found"
    print "\n The Whole trace of transcript is also present in \"output_resolution.txt\" file"
    file.close()
    out_file.close()
    
    

def printResult(clauses, finalClause,space):
    if finalClause.parent1 != -1:
        print " "*space+"%s %s [ %s, %s]" % (finalClause.index,finalClause.getList(),finalClause.parent1,finalClause.parent2)
    else:
        print " "*space+"%s %s [Input]" % (finalClause.index,finalClause.getList())
    if finalClause.parent1 != -1:
        printResult(clauses,clauses[finalClause.parent1],space+1)
    if finalClause.parent2 != -1:
        printResult(clauses,clauses[finalClause.parent2],space+1)

def printListWithV(list):
    for p in list:
        sys.stdout.write(p + " v ")
        

def resolvable(clause1,clause2):
    clause1_list = clause1.clause_list
    clause2_list = clause2.clause_list
    for string in clause1_list:
        stripped = string
        stripped = stripped.strip("-")
        
        if string == stripped:
            if ("-"+string) in clause2_list:
                #print "-" ,string, clause2_list
                return True
        else:
            if stripped in clause2_list:
                #print stripped, clause2_list
                return True
    return False

def clauseExists(clauses,curClause):
    for clause in clauses:
        if clause.clause_list==curClause.clause_list:
            return True
    return False
            
def resolve(clause1, clause2, p):
    set1 = copy.deepcopy(clause1.clause_set)
    set2 = copy.deepcopy(clause2.clause_set)
    set1.remove(p)
    
    if(p.strip("-")==p):
        set2.remove("-"+p)
    else:
        set2.remove(p.strip("-"))
    temp = Clause()
    temp.clause_set = set1 | set2
    temp.clause_list = sorted(temp.clause_set)
    temp.length=len(temp.clause_set)
    return temp
    
         
    
def resolution(clauses,out_file):
    global globalvar
    global lineNum
    global maxQueue
    candidates = Q.PriorityQueue()
    lengthC1 = len(clauses)
    i = 0
    while i<lengthC1:
        j=i+1
        while j<lengthC1:
            if resolvable(clauses[i],clauses[j]):
                candidates.put(ResClauses(clauses[i],clauses[j]))
            j+=1
        i+=1
    iter = 1
    while not candidates.empty():
        curPair = candidates.get()
        list1 = curPair.clause1.clause_list
        list2 = curPair.clause2.clause_list
        resolvant = Clause()
        for p in list1:
            stripped = p
            stripped = stripped.strip("-")
            if(p == stripped and ("-"+p) in list2) or (p!=stripped and stripped in list2):
                print "Iteration %s, queue size %s, resolution on %s and %s" % (iter, candidates.qsize()+1,curPair.clause1.index,curPair.clause2.index)
                out_file.write("Iteration %s, queue size %s, resolution on %s and %s" % (iter, candidates.qsize()+1,curPair.clause1.index,curPair.clause2.index))
                out_file.write("\n")
                iter+=1
                if maxQueue < candidates.qsize()+1:
                    maxQueue=candidates.qsize()+1
                print "resolving %s and %s" %(curPair.clause1.getList(),curPair.clause2.getList())
                out_file.write("resolving %s and %s" %(curPair.clause1.getList(),curPair.clause2.getList()))
                out_file.write("\n")
                resolvant=resolve(curPair.clause1, curPair.clause2, p)
                if len(resolvant.clause_list)==0:
                    resolvant.parent1=curPair.clause1.index
                    resolvant.parent2=curPair.clause2.index
                    resolvant.index=globalvar
                    print "success empty clause found!"
                    print "Max Queue Size %d"% maxQueue
                    print "Total Number of iterations %d" %iter
                    out_file.write("\nSuccess empty clause found! \n")
                    out_file.write("Max Queue Size %d \n"% maxQueue)
                    out_file.write("Total Number of iterations %d" %iter)
                    return resolvant
                if(not clauseExists(clauses, resolvant)):
                    print "%d: %s generated from %d and %d" %(lineNum, resolvant.getList(),curPair.clause1.index,curPair.clause2.index)
                    print
                    out_file.write("%d: %s generated from %d and %d" %(lineNum, resolvant.getList(),curPair.clause1.index,curPair.clause2.index))
                    out_file.write("\n\n")
                    lineNum+=1
                    resolvant.parent1 = curPair.clause1.index
                    resolvant.parent2 = curPair.clause2.index
                    resolvant.index = globalvar
                    clauses.append(resolvant)
                    globalvar+=1
                    i=0
                    while i<globalvar-1:
                        if resolvable(clauses[i], resolvant):
                            candidates.put(ResClauses(resolvant, clauses[i]))
                        i+=1
                else:
                    print "Discard %s generated as it already exists in clauses" %resolvant.getList()
                    print
                    out_file.write("Discard %s generated as it already exists in clauses" %resolvant.getList())
                    out_file.write("\n")
                    out_file.write("\n")
    return False
            
                
        
        
    print candidates.qsize()
    
            
                
if __name__ == "__main__":
    main()         

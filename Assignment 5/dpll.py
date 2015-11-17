from sys import argv
import sys
import copy
from __builtin__ import list
from matplotlib.cbook import Null


globalvar = 0
totalIterations = 0
gloClauses = []
class Clause:
    def __init__(self):
        self.clause_list = {}
        self.length = 0
        self.index = -1
    
    def getList(self):
        temp=""
        for p, n in self.clause_list.iteritems():
            if n=="-":
                p1 = "-"+p
            else:
                p1 = p
            temp = temp + p1+" v "
        if len(temp) > 3:
            temp=temp[:-3]
        if temp == "":
            return "[]"
        return temp
    
    


def main():
    global gloClauses
    global globalvar
    clauses = []
    propSymbols = set()
    model={}
    filename = argv[1]
    jobs =""
    if len(sys.argv)>=5:
        jobs=argv[2]
        if(argv[3]=="T"):
            isPure = True
        else:
            isPure = False
        if(argv[4]=="T"):
            isUnit = True
        else:
            isUnit = False
    elif(len(sys.argv) == 4):
        if(argv[2]=="T"):
            isPure = True
        else:
            isPure = False
        if(argv[3]=="T"):
            isUnit = True
        else:
            isUnit = False
                    
    file = open(filename, 'r')
    global totalIterations
    #out_file = open("output_resolution.txt",'w')
    length = 0
    print "Initial Clauses:"
    for line in file:
        if line.strip() and not line[0] == "#":
            #sys.stdout.write("%s: %s" %(lineNum, line))
            
            words = line.split()
            temp = Clause()
            length = 0
            for word in words:
                tempWord = word.strip("-")
                if(tempWord==word):
                    temp.clause_list[word]="+"
                    propSymbols.add(word)
                else:
                    temp.clause_list[tempWord]="-"
                    propSymbols.add(tempWord) 
                length+=1
            temp.length=length
            print "%s: ( %s )" %(globalvar, temp.getList())
            temp.index = globalvar
            globalvar+=1
            clauses.append(temp)
    print "The Input Facts"
    if(jobs != Null):
        for job in jobs.split():
            temp = Clause()
            temp.clause_list[job]="+"
            temp.length = 1
            temp.index = globalvar
            print "%s: ( %s )" %(globalvar, temp.getList())
            globalvar+=1
            clauses.append(temp)
    gloClauses = copy.deepcopy(clauses)
    print "Props: "+ str(list(propSymbols)) 
    print "------------------------"       
    t,model = DPLL(clauses,propSymbols,model,isPure,isUnit)
    print model
    print "------------------------"  
    
    print "Nodes Searched: %s"%totalIterations
    print "Full Solution"
    aTeam = []
    for p, b in model.iteritems():
        print "%s = %s"%(p,b)
        if(len(p)==1 and b==True):
            aTeam.append(p)
    for prop in propSymbols:
        if(prop not in model):
            print" %s = True/False"%prop
    print "------------------------"  
    sys.stdout.write ("Agent Team: ")
    for a in aTeam:
        sys.stdout.write(a + " ")
    
    
    
def firstAndRest(propSymbols):
    set1 = set()
    set1 = copy.deepcopy(propSymbols)
    list1 = sorted(set1)
    set1.remove(list1[0])
    return list1[0],set1

def filterClauses(clauses,p,t):
    filClauses = []
    delClauses = []
    filClauses = copy.deepcopy(clauses)
    for clause in filClauses:
        clause_list ={}
        clause_list = clause.clause_list
        if p in clause_list:
            if clause_list[p]=="+" and t == True:
                delClauses.append(clause)
            elif clause_list[p]=="-" and t == False:
                delClauses.append(clause)
            else:
                clause_list.pop(p)
    for clause in delClauses:
        filClauses.remove(clause)
    return filClauses
                

def findPureSymbol(clauses,propSymbols,model): 
    for p in propSymbols:
        flag = 2
        for clause in clauses:
            clause_list = clause.clause_list
            if p in clause_list:
                if (clause_list[p]=="+" and flag == 1) or (clause_list[p]=="-" and flag == 0):
                    continue
                if (clause_list[p]=="+" and flag == 0) or (clause_list[p]=="-" and flag == 1):
                    flag = 2
                    break
                if (flag == 2):
                    if(clause_list[p] == "+"):
                        flag = 1
                    else:
                        flag = 0 
        if flag == 1:
            return p,True 
        if flag == 0:
            return p,False
    return Null , False                  
     
def findUnitClause(clauses,propSymbols,model):    
    for clause in clauses:
        clause_list = clause.clause_list
        if(len(clause_list) == 1):
            keys = clause_list.keys()
            if(clause_list[keys[0]] == "+"):
                return clause.index,keys[0],True
            else:
                return clause.index,keys[0],False
            
    return -1,Null,False
             
def DPLL(clauses,propSymbols,model,isPure,isUnit):
    global gloClauses
    noTrue = 0
    global totalIterations
    totalIterations=totalIterations+1
    for clause in clauses:
        allFalse = 1
        cList = clause.clause_list
        for p, n in cList.iteritems():
            if p in model:
                if n=="+" and model[p]==True:
                    allFalse = 0
                    break
                elif n=="-" and model[p]==False:
                    allFalse = 0
                    break
            else:
                allFalse = 0
                noTrue = 1
        if(allFalse == 1):
            print "Clause: %s Fails"%(gloClauses[clause.index].getList())
            return str(False),model
    if(noTrue == 0):
        return str(True), model
    
    if(isPure == True):
        p,value = findPureSymbol(clauses,propSymbols,model)
        if(p != Null):
            model[p] = value
            filPClauses = filterClauses(clauses,p,value)
            restProps = set()
            restProps = copy.deepcopy(propSymbols)
            restProps.remove(p)
            print "Pure Symbol on %s= %s"%(p, value)
            print "Model= %s" %model
    #         totalIterations=totalIterations+1
            return DPLL(filPClauses, restProps, model,isPure,isUnit)
    if(isUnit == True):
        ind,p,value = findUnitClause(clauses,propSymbols,model)
        if(p != Null):
            model[p] = value
            print "Unit Clause on %s gives  %s=%s"%( str((gloClauses[ind]).getList()),p, value)
            print "Model= %s" %model
            filUClauses = filterClauses(clauses,p,value)
            restProps = set()
            restProps = copy.deepcopy(propSymbols)
            restProps.remove(p)
            
    #         totalIterations=totalIterations+1
            return DPLL(filUClauses, restProps, model,isPure,isUnit)
    
    model1 = copy.deepcopy(model)
    p,rest = firstAndRest(propSymbols)
    model1[p]=True
    filTClauses = filterClauses(clauses,p,True)
#     totalIterations=totalIterations+1
    
    print "trying %s = True"%(p)
    print "Model= %s" %model1
    t, model1 = DPLL(filTClauses, rest, model1,isPure,isUnit)
    if(t=="True"):
        return str(True),model1
    print "backtracking"
    model2 = copy.deepcopy(model)
    model2[p]=False
    filFClauses = filterClauses(clauses,p,False)
#     totalIterations=totalIterations+1
    
    print "trying %s = False"%(p)
    print "Model= %s" %model2
    f, model2 = DPLL(filFClauses, rest, model2,isPure,isUnit)
    if(f=="True"):
        return str(True),model2
    else:
        return str(False),model
    
        
                
      
            
    
if __name__ == "__main__":
    main()  
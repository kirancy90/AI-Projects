import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


class Node{
	ArrayList<Stack<Character>> stacks;
	int nBlocks;
	int nStacks;
	int depth;
	int gH;
	Node parent;
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node(ArrayList<Stack<Character>> stacks,int nBlocks,int nStacks){
		this.nBlocks=nBlocks;
		this.nStacks=nStacks;
		this.stacks=new ArrayList<Stack<Character>>();
		for(int i=0;i<nStacks;i++){
			Stack<Character> temp= new Stack<Character>();
			temp.addAll(stacks.get(i));
			this.stacks.add(temp);
		}
		calculateHeur();
	}
	public Node(ArrayList<Stack<Character>> stacks,int nBlocks,int nStacks,int depth){
		this.nBlocks=nBlocks;
		this.nStacks=nStacks;
		this.stacks=new ArrayList<Stack<Character>>();
		for(int i=0;i<nStacks;i++){
			Stack<Character> temp= new Stack<Character>();
			temp.addAll(stacks.get(i));
			this.stacks.add(temp);
		}
		this.depth=depth;
		calculateHeur();
	}
	public ArrayList<Node> adjacencyList(){
		ArrayList<Node> adjacent = new ArrayList<Node>();
//		ArrayList<Stack<Character>> temp=new ArrayList<Stack<Character>>(stacks);
		for(int i=0;i<nStacks;i++){
			if(stacks.get(i).size()>0){
				Node temp=new Node(stacks,nBlocks,nStacks);
				for(int j=0;j<nStacks;j++){
					if(j!=i){
						temp.stacks.get(j).push(temp.stacks.get(i).pop());
						adjacent.add(new Node(temp.getStacks(),nBlocks,nStacks,this.getDepth()+1));
						temp.stacks.get(i).push(temp.stacks.get(j).pop());
					}
				}
				
			}
		}
		return adjacent;
	}
	public ArrayList<Stack<Character>> getStacks() {
		return stacks;
	}
	public void setStacks(ArrayList<Stack<Character>> stacks) {
		Collections.copy(this.stacks, stacks);
	}
	public int getnBlocks() {
		return nBlocks;
	}
	public void setnBlocks(int nBlocks) {
		this.nBlocks = nBlocks;
	}
	public int getnStacks() {
		return nStacks;
	}
	public void setnStacks(int nStacks) {
		this.nStacks = nStacks;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getgH() {
		return gH;
	}
	public void setgH(int gH) {
		this.gH = gH;
	}
	public void calculateHeur(){
		int nStacks=this.nStacks;
		int nBlocks=this.nBlocks;
		ArrayList<Stack<Character>> stacks=new ArrayList<Stack<Character>>(this.stacks);
		int heur=0;
		Iterator<Character> iter=stacks.get(0).iterator();
		int i=0;
//		System.out.println(stacks.get(0).peek());
		while(iter.hasNext()){
			if(iter.next()==(char)(i+65))
				heur--;
			else
				break;
			i++;
		}
		heur=heur+nBlocks-i+1;
//		for(int j=1;j<nStacks;j++){
//			heur=heur+stacks.get(j).size();
//		}
//		System.out.println(heur);
		int mul = nBlocks-nStacks;
		if(mul<=0)
			mul=1;
		heur=heur*(mul);
		heur=heur+this.getDepth();
		this.setgH(heur);
		
	} 
	public void calculateSimpleHeur(){
		int nStacks=this.nStacks;
		int nBlocks=this.nBlocks;
		ArrayList<Stack<Character>> stacks=new ArrayList<Stack<Character>>(this.stacks);
		int heur=0;
		Iterator<Character> iter=stacks.get(0).iterator();
		int i=0;
//		System.out.println(stacks.get(0).peek());
		while(iter.hasNext()){
			if(iter.next()!=(char)(i+65))
				heur++;
			i++;
		}
		heur=heur+nBlocks-i+1;
		heur=heur+this.getDepth();
		this.setgH(heur);
	}
	public void calculateSecondHeur(){
		int nStacks=this.nStacks;
		int nBlocks=this.nBlocks;
		ArrayList<Stack<Character>> stacks=new ArrayList<Stack<Character>>(this.stacks);
		int heur=0;
		Iterator<Character> iter=stacks.get(0).iterator();
		int i=0;
		int diff=nBlocks-nStacks;
		if(diff<=0)
			diff=1;
		while(iter.hasNext()){
			if(iter.next()==(char)(i+65))
				heur=heur-diff;
			else
				break;
			i++;
		}
		int remItem=stacks.get(0).size()-i;
		heur=heur+(remItem)*diff;
		for(int j=1;j<nStacks;j++){
			int flag=0;
			int len=stacks.get(j).size();
			if(isSorted(stacks.get(j))){
				flag=1;
				heur=heur - (int)(len*(diff/3));
			}
			if(len>0){
				if(stacks.get(j).peek()==(char)(i+65)){
					if(flag==1)
						heur=heur-(int)(diff/2)+(int)(diff/3);
					else
						heur=heur-(int)(diff/2);
				}
			}
		}
		heur=heur+this.getDepth();
		this.setgH(heur);
	}
	public boolean isSorted(Stack<Character> stack){
		int size=stack.size();
		if(size==0)
			return false;
		char cur=stack.elementAt(0);
		for(int i=1;i<size;i++){
			if(stack.elementAt(i)<cur){
				cur=stack.elementAt(i);
			}
			else
				return false;
		}
		
		return true;
	}
	
}
public class BlocksWorld {
	public static ArrayList<Node> visited;
	public static PriorityQueue<Node> frontier;
	static class PQsort implements Comparator<Node> {
		  public int compare(Node one, Node two){
			  if(one!=null && two!=null){
				  if(one.getgH()>two.getgH()){
					  return 1;
				  }
				  else{
					  return -1;
				  }
			  }
			  return 0;
		  }
	}

	public static Node solveBlocksWorld(Node initial,char h,PrintWriter writer){
		initial.setDepth(0);
		if(h=='C')
			initial.calculateHeur();
		else if(h=='D')
			initial.calculateSimpleHeur();
		else if(h=='S')
			initial.calculateSecondHeur();
		int maxFrontier=0;
		int iterCount=0;
		if(visited==null){
			visited=new ArrayList<Node>();
		}
		if(frontier==null){
			Comparator<Node> comparator= new PQsort();
			frontier=new PriorityQueue<Node>(11,comparator);
		}
		frontier.add(initial);
		while(frontier.size()!=0){
			if(frontier.size()>maxFrontier)
				maxFrontier=frontier.size();
			Node front=frontier.poll();
			visited.add(front);
			
			iterCount++;
			if(iterCount>15000){
				System.out.println("Goal not found within 15000 Goal Tests!!");
				writer.println("Goal not found within 15000 Goal Tests!!");
				writer.println();
				return null;
			}
				
			if(checkGoal(front)){
				System.out.println();
				System.out.println("Max Frontier : " + maxFrontier);
				System.out.println("Goal Tests: "+ iterCount);
				System.out.println("Depth of Goal Node: "+front.getDepth());
				writer.println();
				writer.println("Success! Depth= "+ front.getDepth()+ " Total_Goal_Tests= "+ iterCount+ " Max Frontier Size= "+ maxFrontier);
				writer.println();
				return front;
			}
			writer.println("Iteration Number= " + iterCount + " Frontier Size= "+ frontier.size()+" f=g+h= "+ front.getgH() + " depth= " + front.getDepth());
			ArrayList<Node> adjacencyList=front.adjacencyList();
			for(Node n:adjacencyList){
				if(!checkVisitedFrontier(n)){
					n.setDepth(front.getDepth()+1);
					n.setParent(front);
					if(h=='C')
						n.calculateHeur();
					else if(h=='D')
						n.calculateSimpleHeur();
					else if(h=='S')
						n.calculateSecondHeur();
					frontier.add(n);
				}
			}
		}
		
		return initial;
	}
	public static void printStacksToFile(Node node,PrintWriter writer){
		for(int i=0;i<node.getnStacks();i++){
			writer.print(i + " | ");
			for(int j=0;j<node.getStacks().get(i).size();j++){
				writer.print(" "+node.getStacks().get(i).elementAt(j) + " ");
			}
			writer.println("");
		}
	}
	public static void printSolution(Node goal){
		Node cur=goal;
		if(cur!=null){
			printSolution(cur.parent);
			System.out.println("THe depth of node below is : "+ cur.getDepth());
			printStacks(cur.getStacks(),cur.getnBlocks(),cur.getnStacks());
			
		}
		
	}
	public static boolean checkGoal(Node s){
		int nBlocks=s.getnBlocks();
		if(s.getStacks().get(0)==null)
			return false;
		if(s.getStacks().get(0).size()!=nBlocks)
			return false;
		for(int i=0;i<nBlocks;i++){
			if(s.getStacks().get(0).elementAt(i)!=(char)(i+65)){
				return false;
			}
		}
		return true;
	}
	public static boolean checkVisitedFrontier(Node s){
		int size=visited.size();
		for(int i=0;i<size;i++){
			if(compareNode(visited.get(i),s)){
				if(visited.get(i).getgH()<s.getgH()){
					return true;
				}
			}
		}
		Iterator<Node> iterator=frontier.iterator();
		while(iterator.hasNext()){
			Node temp=iterator.next();
			if(compareNode(temp,s)){
				if(temp.getgH()<s.getgH()){
					return true;
				}
				else{
					frontier.remove(temp);
					return false;
				}
			}
		}
		return false;
	}
	public static boolean compareNode(Node s, Node d){
		int nStacks=s.nStacks;
		ArrayList<Stack<Character>> sStacks=s.getStacks();
		ArrayList<Stack<Character>> dStacks=d.getStacks();
		for(int i=0;i<nStacks;i++){
			if(sStacks.get(i).size()!=dStacks.get(i).size())
				return false;
			for(int j=0;j<sStacks.get(i).size();j++){
				if(sStacks.get(i).elementAt(j)!=dStacks.get(i).elementAt(j))
					return false;
			}
		}
		return true;
	}
	public static ArrayList<Stack<Character>> generateProblem(ArrayList<Stack<Character>> stacks,int nBlocks, int nStacks){
		Random randomGen=new Random();
		for(int i=0;i<nBlocks;i++){
			int randomInt= randomGen.nextInt(nStacks);
			stacks.get(randomInt).push(stacks.get(0).pop());
		}
		for(int i=0;i<100;i++){
			int randomInt=randomGen.nextInt(nStacks);
			int dRandomInt=randomGen.nextInt(nStacks);
			if(!stacks.get(randomInt).empty()){
				stacks.get(dRandomInt).push(stacks.get(randomInt).pop());
			}
		}
		
		
		return stacks;
	}
	public static void printStacks(ArrayList<Stack<Character>> stacks,int nBlocks, int nStacks){
		for(int i=0;i<nStacks;i++){
			System.out.print(i + " | ");
			for(int j=0;j<stacks.get(i).size();j++){
				System.out.print(" "+stacks.get(i).elementAt(j) + " ");
			}
			System.out.println("");
		}
	}
	public static void main(String args[]){

		int nBlocks= Integer.parseInt(args[0]);
		int nStacks= Integer.parseInt(args[1]);
		
		PrintWriter writer=null;
		String fileStr="";
		try {
			 writer = new PrintWriter("trace_assignment2.txt", "UTF-8");
		     
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Stack<Character>> stacks = new ArrayList<Stack<Character>>();
		
	 	Stack<Character> first= new Stack<Character>();
		for(int i=0;i<nBlocks;i++){
			first.push( (char)(i+65));
		}
		for(int i=0;i<nStacks;i++){
			stacks.add(new Stack<Character>());
		}

		stacks.get(0).addAll(first);
		stacks=generateProblem(stacks,nBlocks,nStacks);
		
		Node defHeurNode = new Node(stacks,nBlocks,nStacks,0);
		Node firstCustHeurNode = new Node(stacks,nBlocks,nStacks,0);
		Node secCustHeurNode = new Node(stacks,nBlocks,nStacks,0);
		
		System.out.println("The initial State is: ");
		printStacks(defHeurNode.getStacks(),nBlocks,nStacks);
		writer.println("This is the initial State");
		printStacksToFile(defHeurNode,writer);
		
		firstCustHeurNode.setParent(null);
		defHeurNode.setParent(null);
		secCustHeurNode.setParent(null);
		
		
		writer.println("The Below trace is for Custom Heuristic function");
		writer.println("");
		System.out.println();
		System.out.println("These are the results for Custom Heuristic fucntion");
		Node secCustFinalNode = solveBlocksWorld(secCustHeurNode,'S',writer);
		if(secCustFinalNode != null){
			System.out.println("The Solution Path for Custom Heuristic Function is :");
			System.out.println();
			printSolution(secCustFinalNode);
			System.out.println("--------------------------------------------------");
		}
		visited.clear();
		frontier.clear();
		
		writer.println("The Below trace is for Modified Out of Place Heuristic function");
		writer.println("");
		System.out.println();
		System.out.println("These are the results for Modified Out of Place Heuristic fucntion");
		Node firstCustFinalNode = solveBlocksWorld(firstCustHeurNode,'C',writer);
		if(firstCustFinalNode !=null){
			System.out.println("The Solution Path for Modified Out of Place Heuristic Fucntion is : ");
			System.out.println();
			printSolution(firstCustFinalNode);	
			System.out.println("--------------------------------------------------");
		}
		visited.clear();
		frontier.clear();
		writer.println("The Below trace is for Normal Out of Place Heuristic function");
		writer.println("");
		System.out.println();
		System.out.println("These are the results for Normal Out of Place Heuristic fucntion");
		Node defFinalNode = solveBlocksWorld(defHeurNode,'D',writer);
		if(defFinalNode !=null){
			System.out.println("The Solution Path for Normal Out of Place Heuristic Fucntion is : ");
			System.out.println();
			printSolution(defFinalNode);
			System.out.println("--------------------------------------------------");
		}
		writer.close();
	}
}

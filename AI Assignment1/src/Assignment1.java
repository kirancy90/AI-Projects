import java.io.*;
import java.util.*;

public class Assignment1 {
	public static int heurCount=1;
	public static Map<Integer, Node> nodeCordMap;
	public static PriorityQueue<Node> frontier;
	public static ArrayList<Integer> visited;
	static class PQsort implements Comparator<Node> {
		  public int compare(Node one, Node two){
			  if(one!=null && two!=null){
				  if(one.getHeur()>two.getHeur()){
					  return 1;
				  }
				  else{
					  return -1;
				  }
			  }
			  return 0;
		  }
	}
	public static class Node {
		int v;
		int x;
		int y;
		float goalDistance;
		Node parent;
		int depth;
		float heur;
		ArrayList<Node> adjacencyList;
		public Node(int i) {
			v=i;
		}

		public Node(int i, Node p) {
			v=i;
			parent=p;
		}

		public float getGoalDistance() {
			return goalDistance;
		}

		public void setGoalDistance(float goalDistance) {
			this.goalDistance = goalDistance;
		}

		public void setGoalDist(Node finalState){
			double dist= Math.sqrt(((finalState.getX()-x)*(finalState.getX()-x)) + ((finalState.getY()-y)*(finalState.getY()-y)));
			setGoalDistance((float)dist);
		}
		
		public int getV() {
			return v;
		}

		public void setV(int v) {
			this.v = v;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}
		public int getDepth() {
			return depth;
		}

		public void setDepth(int depth) {
			this.depth = depth;
		}

		public float getHeur() {
			return heur;
		}

		public void setHeur(float heur) {
			this.heur = heur;
		}
		public ArrayList<Node> successors() {
			if (getAdjacencyList() == null) {
				return new ArrayList<Node>();
			}
			return getAdjacencyList();
		}

		public ArrayList<Node> getAdjacencyList() {
			return adjacencyList;
		}

		public void setAdjacencyList(ArrayList<Node> adjacencyList) {
			this.adjacencyList = adjacencyList;
		}

		public ArrayList<Node> traceBack() {
			ArrayList<Node> traceNodes = new ArrayList<Node>();
			Node cur=this;
			if(cur.parent==null)
				return traceNodes;
			while(cur!=null){
				traceNodes.add(cur);
				cur=cur.getParent();
			}
			Collections.reverse(traceNodes);
			return traceNodes;
		}
	}

	public static void inputGraph(int x1,int y1,int x2,int y2,String searchType,String fileName) {
		int vertices = 0;
		int edges = 0;
		int edge=0;
		int v1=0,v2=0;
		int vertex=0;
		int x=0,y=0;
		Node node;
		Node initialState = null;
		Node finalState = null;
		File graphIn = new File(System.getProperty("user.dir") + "/" +fileName);
		if(!graphIn.exists())
			System.out.println("File Does not exit");
		else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(graphIn));
				String line;
				line = br.readLine();
				String[] parts = line.split(" ");
				vertices = Integer.parseInt(parts[1]);
				int i = 1;
				while (i <= vertices) {
					line = br.readLine();
					parts = line.split(" ");
					vertex=Integer.parseInt(parts[0]);
					x=Integer.parseInt(parts[1]);
					y=Integer.parseInt(parts[2]);
					node=new Node(vertex);
					node.setX(x);
					node.setY(y);
					if(x==x1 && y==y1){
						initialState=node;
					}
					if(x==x2 && y==y2){
						finalState=node;
					}
					if(nodeCordMap == null){
						nodeCordMap=new HashMap<Integer,Node>();
					}
					nodeCordMap.put(vertex, node);
					i++;
				}
				line = br.readLine();
				parts = line.split(" ");
				edges = Integer.parseInt(parts[1]);
				i=1;
				while(i<=edges){
					line=br.readLine();
					parts = line.split(" ");
					edge = Integer.parseInt(parts[0]);
					v1 = Integer.parseInt(parts[1]);
					v2 = Integer.parseInt(parts[2]);
					Node v1Node=nodeCordMap.get(v1);
					Node v2Node=nodeCordMap.get(v2);
					ArrayList<Node> adjacency = v1Node.getAdjacencyList();
					if(adjacency == null)
						adjacency=new ArrayList<Node>();
					adjacency.add(v2Node);
					v1Node.setAdjacencyList(adjacency);
					
					adjacency = v2Node.getAdjacencyList();
					if(adjacency == null)
						adjacency=new ArrayList<Node>();
					adjacency.add(v1Node);
					v2Node.setAdjacencyList(adjacency);
					i++;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(initialState!=null && finalState!=null){
				Node result=search(initialState,finalState,searchType,vertices);
				
			}
			else{
				System.out.println("Input out of bounds");
			}
		}

	}
	public static float calculateHeur(char searchType,Node current,Node dest){
		if(searchType=='G'){
			float ret= (float)(((dest.getX()-current.getX())*(dest.getX()-current.getX())) + ((dest.getY()-current.getY())*(dest.getY()-current.getY())));
			return ret;
		}
		if(searchType=='B'){
			float ret= (float)heurCount;
			return ret;
		}
		if(searchType=='D'){
			float ret= (float)1/heurCount;
			return ret;
			
		}
	return 0;
	}
	public static Node search(Node initialState,Node finalState,String searchType,int totalV){
		PrintWriter writer=null;
		int iterCount=0;
		float distFromGoal = 0;
		String fileStr="";
		try {
			 writer = new PrintWriter("diagnostic.txt", "UTF-8");
		     writer.println("Search Type: "+ searchType+ " Source Vertex: ("+ initialState.getX()+", " +initialState.getY()+") Goal Vertex: ("+ finalState.getX()+", "+finalState.getY()+")");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char searchInput=searchType.charAt(0);
		int maxFrontier=0;
		initialState.setHeur(1);
		initialState.setDepth(0);
		
		if(visited==null){
			visited=new ArrayList<Integer>();
		}
		if(frontier==null){
			Comparator<Node> comparator= new PQsort();
			frontier=new PriorityQueue<Node>(11,comparator);
		}
		frontier.add(initialState);
//		visited.add(initialState.getV());
		while(frontier.size()>0){
			if(frontier.size()>maxFrontier)
				maxFrontier=frontier.size();
			Node front=frontier.poll();
			visited.add(front.getV());
			iterCount++;
			if(front==finalState){
				printNodes(front.traceBack());
				System.out.println("Search Algorithm: "+ searchType);
				System.out.println("Total Iterations: "+ iterCount);
				System.out.println("Max Frontier Size: "+ maxFrontier);
				System.out.println("Vertices Visited: "+ (iterCount+frontier.size()) + "/"+ totalV);
				System.out.println("Path Length: "+ finalState.getDepth());
				fileStr= "iter= "+iterCount+ " frontier= "+frontier.size()+" popped= "+front.getV()+" ("+front.getX()+" ," +front.getY() + " )" + "depth= "+ front.getDepth() + " Dist2goal= "+ 0;
				writer.println(fileStr);
				writer.close();
				return front;
			}
			
			front.setGoalDist(finalState);
			distFromGoal = front.getGoalDistance();
			fileStr= "iter= "+iterCount+ " frontier= "+frontier.size()+" popped= "+front.getV()+" ("+front.getX()+" ," +front.getY() + " )" + "depth= "+ front.getDepth() + " Dist2goal= "+ distFromGoal;
			writer.println();
			writer.println(fileStr);
			ArrayList<Node> successors = front.getAdjacencyList();
				for(Node n: successors){
					if(!visited.contains(n.getV()) && !frontier.contains(n)){
						n.setHeur(calculateHeur(searchInput,n,finalState));
						//n.setHeur((float)1/heurCount);
						heurCount++;
						n.setParent(front);
						n.setDepth(front.getDepth()+1);
						frontier.add(n);
						fileStr= "pushed "+ n.getV()+ " ("+ n.getX()+ " ,"+ n.getY()+ " )";
						writer.println(fileStr);
//						System.out.println(n.getV());
						
					}
					else{
						fileStr = "Node "+ n.getV() + " ("+ n.getX() + ", "+ n.getY() + ") is discarded because it is already in the Visited list or in the Frontier";
						
						writer.println(fileStr);
						
					}
				}
		}
		writer.close();
		printNodes(finalState.traceBack());
		return finalState;
	}
	public static void printNodes(ArrayList<Node> trace){
		if(trace.size()==0)
			System.out.println("There is No path between the Initial and Final Node");
		else{
			System.out.println("Solution Path is");
			for(Node n: trace){
				System.out.println("Vertex "+ n.getV()+ " ("+n.getX() + " "+n.getY()+ ")");
			}
		}
	}
	public static void main(String[] args) {
		String searchType=args[0];
		String fileName = args[1];
		int x1= Integer.parseInt(args[2]);
		int y1= Integer.parseInt(args[3]);
		int x2= Integer.parseInt(args[4]);
		int y2= Integer.parseInt(args[5]);

		if(x1==x2 && y1==y2)
			System.out.println("Initial Node is same as final Node");
		else
			inputGraph(x1,y1,x2,y2,searchType,fileName);
		

	}
}

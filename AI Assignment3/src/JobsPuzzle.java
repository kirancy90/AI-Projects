import java.util.*;

public class JobsPuzzle {
	public static int totalStatesCSP=0;
	public static int totalStatesMRV=0;
//	public static HashMap<String,List<String>> constraints;
	public static HashMap<String,List<String>> varChars=new HashMap<String,List<String>>();
	public static HashMap<String,List<String>> domChars=new HashMap<String,List<String>>();
	public static void generateConstraints(){

	}
	public static boolean checkConsistency(HashMap<String, String> assign,String var, String val){
		List<String> vChars=varChars.get(var);
		List<String> dChars=domChars.get(val);
		if(vChars.get(0)!=null && vChars.get(0)!=""){
			if(!vChars.get(0).equals(dChars.get(0))){
				return false;
			}
		}
		if( vChars.get(1)!=null && vChars.get(1)=="Y"){
			if(!vChars.get(1).equals(dChars.get(1))){
				return false;
			}
		}
		int count=0;
		for(String vars:assign.keySet()){
			if(assign.get(vars).equals(val))
				count++;
		}
		if(count>=2)
			return false;
		if(val.equals("R") && (var.equals("boxer") || var.equals("chef") || var.equals("police") ))
			return false;
		if(var.equals("chef") && val.equals(assign.get("police")))
			return false;
		if(var.equals("police") && val.equals(assign.get("chef")))
			return false;
		return true;
	}
	public static boolean assignComplete(HashMap<String,String> assign){
		for(String val:assign.values()){
			if(val == null || val == ""){
				return false;
			}
		}
		
		return true;
	}
	public static String getNextUnassignedVar(HashMap<String,String> assign){
		for(String key:assign.keySet()){
			if(assign.get(key) == null || assign.get(key) == ""){
				return key;
			}
		}
		return "";
		
	}
	public static String getNextUnassignedVarMRV(HashMap<String,String> assign,ArrayList<String> cspDom){
		int min=5;
		int cur=0;
		String retKey="";
		for(String key:assign.keySet()){
			if(assign.get(key) == null || assign.get(key) == ""){
				for(int i=0;i<cspDom.size();i++){
					if(checkConsistency(assign,key,cspDom.get(i))){
						cur++;
					}
				}
				if(cur<min){
					min=cur;
					retKey=key;
				}
				cur=0;
			}
		}
		return retKey;
		
	}
	public static HashMap<String, String> backtrackCSP(HashMap<String, String> assign, ArrayList<String> cspVar,ArrayList<String> cspDom){
		int domSize=cspDom.size();
		if(assignComplete(assign))
			return assign;
		String var=getNextUnassignedVar(assign);
		for(int i=0;i<domSize;i++){
			String val=cspDom.get(i);
			if(checkConsistency(assign,var,val)){
				assign.put(var, val);
				totalStatesCSP++;
				assign=backtrackCSP(assign,cspVar,cspDom);
				if(assignComplete(assign)){
					return assign;
				}
			}
			assign.put(var, "");
		}
		return assign;
	}
	public static HashMap<String,String> backtrackMRV(HashMap<String, String> assign, ArrayList<String> cspVar,ArrayList<String> cspDom){
		int domSize=cspDom.size();
		if(assignComplete(assign))
			return assign;
		String var=getNextUnassignedVarMRV(assign,cspDom);
		for(int i=0;i<domSize;i++){
			String val=cspDom.get(i);
			if(checkConsistency(assign,var,val)){
				assign.put(var, val);
				totalStatesMRV++;
				assign=backtrackMRV(assign,cspVar,cspDom);
				if(assignComplete(assign)){
					return assign;
				}
			}
			assign.put(var, "");
		}
		return assign;
	}
	public static void printResult(HashMap<String,String> assign){
		System.out.println("The Assignment of Job to People are as follows");
		for(String key:assign.keySet()){
			System.out.println(key + " is " + assign.get(key));
		}
	}
	public static void main(String args[]){
		HashMap<String, String> assignCSP = new HashMap<String, String>();
		HashMap<String, String> assignMRV = new HashMap<String, String>();
		ArrayList<String> cspVar=new ArrayList<String>();
		ArrayList<String> cspDom=new ArrayList<String>();
		
		assignCSP.put("chef", "");
		assignCSP.put("guard", "");
		assignCSP.put("nurse", "");
		assignCSP.put("clerk", "");
		assignCSP.put("police", "");
		assignCSP.put("teacher", "");
		assignCSP.put("actor", "");
		assignCSP.put("boxer", "");
		
		assignMRV.put("chef", "");
		assignMRV.put("guard", "");
		assignMRV.put("nurse", "");
		assignMRV.put("clerk", "");
		assignMRV.put("police", "");
		assignMRV.put("teacher", "");
		assignMRV.put("actor", "");
		assignMRV.put("boxer", "");
		
		
		
		cspDom.add("T");
		domChars.put("T", new ArrayList<String>());
		domChars.get("T").add("F");  //Male or Female
		domChars.get("T").add("Y");  //Educated or not
		
		
		
		cspDom.add("S");
		domChars.put("S", new ArrayList<String>());
		domChars.get("S").add("M");  //Male or Female
		domChars.get("S").add("Y");  //Educated or not
		
		cspDom.add("R");
		domChars.put("R", new ArrayList<String>());
		domChars.get("R").add("F");  //Male or Female
		domChars.get("R").add("Y");  //Educated or not
		
		cspDom.add("P");
		domChars.put("P", new ArrayList<String>());
		domChars.get("P").add("M");  //Male or Female
		domChars.get("P").add("N");  //Educated or not
		
		cspVar.add("chef");
		varChars.put("chef", new ArrayList<String>());
		varChars.get("chef").add("F"); //Male or Female or Not known
		varChars.get("chef").add("N"); //Education required or not
	
		cspVar.add("guard");
		varChars.put("guard", new ArrayList<String>());
		varChars.get("guard").add(""); //Male or Female or Not known
		varChars.get("guard").add("N"); //Education required or not
		
		cspVar.add("nurse");
		varChars.put("nurse", new ArrayList<String>());
		varChars.get("nurse").add("M"); //Male or Female or Not known
		varChars.get("nurse").add("Y"); //Education required or not
		
		cspVar.add("teacher");
		varChars.put("teacher", new ArrayList<String>());
		varChars.get("teacher").add(""); //Male or Female or Not known
		varChars.get("teacher").add("Y"); //Education required or not
		
		cspVar.add("clerk");
		varChars.put("clerk", new ArrayList<String>());
		varChars.get("clerk").add("M"); //Male or Female or Not known
		varChars.get("clerk").add("N"); //Education required or not
		
		cspVar.add("police");
		varChars.put("police", new ArrayList<String>());
		varChars.get("police").add(""); //Male or Female or Not known
		varChars.get("police").add("Y"); //Education required or not
		
		
		
		cspVar.add("boxer");
		varChars.put("boxer", new ArrayList<String>());
		varChars.get("boxer").add(""); //Male or Female or Not known
		varChars.get("boxer").add("N"); //Education required or not
		
		cspVar.add("actor");
		varChars.put("actor", new ArrayList<String>());
		varChars.get("actor").add("M"); //Male or Female or Not known
		varChars.get("actor").add("N"); //Education required or not
		
		Collections.shuffle(cspDom);
		Collections.shuffle(cspVar);

		assignCSP=backtrackCSP(assignCSP,cspVar,cspDom);
		
		assignMRV=backtrackMRV(assignMRV,cspVar,cspDom);
		System.out.println("The solution for Jobs puzzle with Normal CSP Algorithm is:");
		printResult(assignCSP);
		System.out.println("Total States = "+totalStatesCSP);
		System.out.println();
		
		System.out.println("The solution for Jobs puzzle with MRV Algorithm is:");
		printResult(assignMRV);
		System.out.println("Total States = "+totalStatesMRV);
		System.out.println();
	}
}

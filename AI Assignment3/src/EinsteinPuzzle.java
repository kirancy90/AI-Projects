import java.util.*;
public class EinsteinPuzzle {
	
	public static int totalStatesCSP=0;
	public static int totalStatesMRV=0;
	public static int[] nextUnassignedVar(HashMap<Integer,ArrayList<String>> assign){
		int[] ret=new int[2];
		for(Integer key:assign.keySet()){
			for(int i=0;i<5;i++){
				if(assign.get(key).get(i)==null || assign.get(key).get(i)==""){
					ret[0]=key;
					ret[1]=i;
					return ret;
				}
			}
			
		}
		
		return ret;
	}
	public static boolean assignComplete(HashMap<Integer,ArrayList<String>> assign){
		for(Integer key:assign.keySet()){
			for(int i=0;i<5;i++){
				if(assign.get(key).get(i)==null || assign.get(key).get(i)=="")
					return false;
			}
		}
		return true;
	}
	public static boolean checkConsistency(HashMap<Integer,ArrayList<String>> assign,int house,String var,String val,int varNo){
		
		
		//Generic Constraint
		for(int key:assign.keySet()){
			if(assign.get(key).get(varNo)!=null && assign.get(key).get(varNo).equalsIgnoreCase(val)){
				return false;
			}
		}
		//Constraint number 1
		if(var.equalsIgnoreCase("color") && val.equalsIgnoreCase("red")){
			if(assign.get(house).get(0)!=null && !assign.get(house).get(0).equalsIgnoreCase("englishman")){
				return false;
			}
		}
		if(var.equalsIgnoreCase("nat") && val.equalsIgnoreCase("englishman")){
			if(assign.get(house).get(2)!=null && !assign.get(house).get(2).equalsIgnoreCase("red")){
				return false;
			}
		}
		//Constraint number 2
		if(var.equalsIgnoreCase("animal") && val.equalsIgnoreCase("dog")){
			if(assign.get(house).get(0)!=null && !assign.get(house).get(0).equalsIgnoreCase("spaniard")){
				return false;
			}
		}
		if(var.equalsIgnoreCase("nat") && val.equalsIgnoreCase("spaniard")){
			if(assign.get(house).get(1)!=null && !assign.get(house).get(1).equalsIgnoreCase("dog")){
				return false;
			}
		}
		//Constraint number 3
		if(var.equalsIgnoreCase("nat") && val.equals("norwegian")){
			if(house!=1)
				return false;		
		}
		if(house==1){
			if(var.equalsIgnoreCase("nat") && !val.equalsIgnoreCase("norwegian")){
				return false;
			}
		}
		//Constraint number 4
		if(var.equalsIgnoreCase("color") && val.equalsIgnoreCase("green")){
			if(house==1)
				return false;
			if(assign.get(house-1).get(2)!=null && !assign.get(house-1).get(2).equalsIgnoreCase("ivory")){
				return false;
			}
			for(int key:assign.keySet()){
				if(assign.get(key).get(2)!=null && assign.get(key).get(2).equalsIgnoreCase("ivory") && key!=house-1){
					return false;
				}
			}
		}
		if(var.equalsIgnoreCase("color") && val.equalsIgnoreCase("ivory")){
			if(house==5)
				return false;
			if(assign.get(house+1).get(2)!=null && !assign.get(house+1).get(2).equalsIgnoreCase("green")){
				return false;
			}
			for(int key:assign.keySet()){
				if(assign.get(key).get(2)!=null && assign.get(key).get(2).equalsIgnoreCase("green") && key!=house+1){
					return false;
				}
			}
		}
		//Constraint number 5
		if(var.equalsIgnoreCase("eats") && val.equalsIgnoreCase("hershey")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(1)!=null && assign.get(key).get(1).equalsIgnoreCase("fox")){
					if( !(key==(house-1) || key==(house+1)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("animal") && val.equalsIgnoreCase("fox")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(3)!=null && assign.get(key).get(3).equalsIgnoreCase("hershey")){
					if( !(key==(house-1) || key==(house+1)) ){
						return false;
					}
				}
			}
		}
		//Constraint Number 6
		if(var.equalsIgnoreCase("eats") && val.equalsIgnoreCase("kitkats")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(2)!=null && assign.get(key).get(2).equalsIgnoreCase("yellow")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("color") && val.equalsIgnoreCase("yellow")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(3)!=null && assign.get(key).get(3).equalsIgnoreCase("kitkats")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		//Constraint Number 7
		if(var.equalsIgnoreCase("nat") && val.equalsIgnoreCase("norwegian")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(2)!=null && assign.get(key).get(2).equalsIgnoreCase("blue")){
					if( !((key==(house-1)) || (key==(house+1))) ) {
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("color") && val.equalsIgnoreCase("blue")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(0)!=null && assign.get(key).get(0).equalsIgnoreCase("norwegian")){
					if( !((key==(house-1)) || (key==(house+1))) ) {
						return false;
					}
				}
			}
		}
		//Constraint Number 8
		if(var.equalsIgnoreCase("eats") && val.equalsIgnoreCase("smarties")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(1)!=null && assign.get(key).get(1).equalsIgnoreCase("snails")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("animal") && val.equalsIgnoreCase("snails")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(3)!=null && assign.get(key).get(3).equalsIgnoreCase("smarties")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		//Constraint Number 9
		if(var.equalsIgnoreCase("eats") && val.equalsIgnoreCase("snickers")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(4)!=null && assign.get(key).get(4).equalsIgnoreCase("orange")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("drinks") && val.equalsIgnoreCase("orange")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(3)!=null && assign.get(key).get(3).equalsIgnoreCase("snickers")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		//Constraint Number 10
		if(var.equalsIgnoreCase("nat") && val.equalsIgnoreCase("ukranian")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(4)!=null && assign.get(key).get(4).equalsIgnoreCase("tea")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("drinks") && val.equalsIgnoreCase("tea")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(0)!=null && assign.get(key).get(0).equalsIgnoreCase("ukranian")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		//Constraint number 11
		if(var.equalsIgnoreCase("nat") && val.equalsIgnoreCase("japanese")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(3)!=null && assign.get(key).get(3).equalsIgnoreCase("milkyways")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("eats") && val.equalsIgnoreCase("milkyways")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(0)!=null && assign.get(key).get(0).equalsIgnoreCase("japanese")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		//Constraint Number 12
		if(var.equalsIgnoreCase("eats") && val.equalsIgnoreCase("kitkats")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(1)!=null && assign.get(key).get(1).equalsIgnoreCase("horse")){
					if( !(key==(house-1) || key==(house+1)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("animal") && val.equalsIgnoreCase("horse")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(3)!=null && assign.get(key).get(3).equalsIgnoreCase("kitkats")){
					if( !(key==(house-1) || key==(house+1)) ){
						return false;
					}
				}
			}
		}
		//Constraint Number 13
		if(var.equalsIgnoreCase("drinks") && val.equalsIgnoreCase("coffee")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(2)!=null && assign.get(key).get(2).equalsIgnoreCase("green")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		if(var.equalsIgnoreCase("color") && val.equalsIgnoreCase("green")){
			for(int key:assign.keySet()){
				if(assign.get(key).get(4)!=null && assign.get(key).get(4).equalsIgnoreCase("coffee")){
					if(!(key==(house)) ){
						return false;
					}
				}
			}
		}
		//Constraint number 14
		if(var.equalsIgnoreCase("drinks") && val.equalsIgnoreCase("milk")){
			if(house!=3)
				return false;
		}
		if(house==3){
			if(var.equalsIgnoreCase("drinks") && !(val.equalsIgnoreCase("milk")) ) {
				return false;
			}
		}
		return true;
	}
	
	public static int[] nextUnassignedVarMrv(HashMap<Integer,ArrayList<String>> assign,HashMap<String,ArrayList<String>> varDoms){
		int[] ret=new int[2];
		int min=5;
		int minHouseNo=0;
		int minVar=0;
		int cur=5;
		for(Integer key:assign.keySet()){
			for(int i=0;i<5;i++){
				cur=0;
				if(assign.get(key).get(i)==null || assign.get(key).get(i)==""){
					for(int j=0;j<5;j++){
						switch(i){
							case 0:
								if(checkConsistency(assign,key,"nat",varDoms.get("nat").get(j),j)){
									cur++;
								}
								break;
							case 1:
								if(checkConsistency(assign,key,"animal",varDoms.get("animal").get(j),j)){
									cur++;
								}
								break;
							case 2:
								if(checkConsistency(assign,key,"color",varDoms.get("color").get(j),j)){
									cur++;
								}
								break;
							case 3:
								if(checkConsistency(assign,key,"eats",varDoms.get("eats").get(j),j)){
									cur++;
								}
								break;
							case 4:
								if(checkConsistency(assign,key,"drinks",varDoms.get("drinks").get(j),j)){
									cur++;
								}
								break;
						}
					}
					if(cur<min){
						min=cur;
						minHouseNo=key;
						minVar=i;
					}
				}
			}
			
		}
		ret[0]=minHouseNo;
		ret[1]=minVar;
		return ret;
	}
	public static HashMap<Integer,ArrayList<String>> backtrackCSPZebra(HashMap<Integer,ArrayList<String>> assign,ArrayList<String> vars,HashMap<String,ArrayList<String>> varDoms){
		if(assignComplete(assign))
			return assign;
		int[] var=nextUnassignedVar(assign);
		int house=var[0];
		String unassignVar=vars.get(var[1]);
		ArrayList<String> domList=varDoms.get(unassignVar);
		for(String val:domList){
			if(checkConsistency(assign,house,unassignVar,val,var[1])){
				
				assign.get(house).set(var[1], val);
				totalStatesCSP++;
				assign=backtrackCSPZebra(assign,vars,varDoms);
				if(assignComplete(assign))
					return assign;
				assign.get(house).set(var[1], null);
				
			}
			
			
		}
		return assign;
	}
	public static HashMap<Integer,ArrayList<String>> backtrackMRVZebra(HashMap<Integer,ArrayList<String>> assign, ArrayList<String> vars,HashMap<String,ArrayList<String>> varDoms){
		if(assignComplete(assign))
			return assign;
		int[] var=nextUnassignedVarMrv(assign,varDoms);
		int house=var[0];
		String unassignVar=vars.get(var[1]);
		ArrayList<String> domList=varDoms.get(unassignVar);
		for(String val:domList){
			if(checkConsistency(assign,house,unassignVar,val,var[1])){
				
				assign.get(house).set(var[1], val);
				totalStatesMRV++;
				assign=backtrackMRVZebra(assign,vars,varDoms);
				if(assignComplete(assign))
					return assign;
				assign.get(house).set(var[1], null);
				
			}			
		}
		return assign;
	}
	public static void printAnswer(HashMap<Integer,ArrayList<String>> assign){
		System.out.println("      Nationality     Animal    Color   Eats    Drinks");
		for(Integer key:assign.keySet()){
			System.out.print("House  "+key +" ");
			for(int i=0;i<5;i++){
				System.out.print(assign.get(key).get(i)+"    ");
			}
			System.out.println();
		}
	}
	public static void main(String args[]){
		HashMap<Integer,ArrayList<String>> assignCSP=new HashMap<Integer,ArrayList<String>>();
		HashMap<Integer,ArrayList<String>> assignMRV=new HashMap<Integer,ArrayList<String>>();
		HashMap<String,ArrayList<String>> varDoms=new HashMap<String,ArrayList<String>>();
		ArrayList<String> vars=new ArrayList<String>();
		ArrayList<String> natDom=new ArrayList<String>();
		ArrayList<String> animalDom=new ArrayList<String>();
		ArrayList<String> colorDom=new ArrayList<String>();
		ArrayList<String> eatsDom=new ArrayList<String>();
		ArrayList<String> drinksDom=new ArrayList<String>();
		
		for(int i=1;i<=5;i++){
			assignCSP.put(i, new ArrayList<String>(5));
			assignCSP.get(i).add(null);
			assignCSP.get(i).add(null);
			assignCSP.get(i).add(null);
			assignCSP.get(i).add(null);
			assignCSP.get(i).add(null);
			
			assignMRV.put(i, new ArrayList<String>(5));
			assignMRV.get(i).add(null);
			assignMRV.get(i).add(null);
			assignMRV.get(i).add(null);
			assignMRV.get(i).add(null);
			assignMRV.get(i).add(null);
		}
		
		vars.add("nat");
		natDom.add("spaniard");
		natDom.add("japanese");
		natDom.add("englishman");
		natDom.add("ukranian");
		natDom.add("norwegian");
		varDoms.put("nat", natDom);

		vars.add("animal");
		animalDom.add("fox");
		animalDom.add("dog");
		animalDom.add("snails");
		animalDom.add("horse");
		animalDom.add("zebra");
		varDoms.put("animal", animalDom);
		
		vars.add("color");
		colorDom.add("red");
		colorDom.add("ivory");
		colorDom.add("yellow");
		colorDom.add("blue");
		colorDom.add("green");
		varDoms.put("color", colorDom);
		
		vars.add("eats");
		eatsDom.add("kitkats");
		eatsDom.add("smarties");
		eatsDom.add("snickers");
		eatsDom.add("hershey");
		eatsDom.add("milkyways");
		varDoms.put("eats", eatsDom);
		
		vars.add("drinks");
		drinksDom.add("orange");
		drinksDom.add("tea");
		drinksDom.add("water");
		drinksDom.add("coffee");
		drinksDom.add("milk");
		varDoms.put("drinks", drinksDom);
		
		
		Collections.shuffle(drinksDom);
		Collections.shuffle(eatsDom);
		Collections.shuffle(colorDom);
		Collections.shuffle(animalDom);
		Collections.shuffle(natDom);
		
		assignCSP=backtrackCSPZebra(assignCSP,vars,varDoms);
		assignCSP=backtrackMRVZebra(assignMRV,vars,varDoms);
		System.out.println("Solution for Einstein's Zebra Puzzle with Normal CSP Algorithm is");
		printAnswer(assignCSP);
		System.out.println("Total States: " + totalStatesCSP);
		
		System.out.println();
		
		System.out.println("Solution for Einstein's Zebra Puzzle with MRV Algorithm is");
		printAnswer(assignMRV);
		System.out.println("Total States: " + totalStatesMRV);
		
	}
}

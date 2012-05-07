
import java.util.LinkedList;


public class LampList<Integer> extends LinkedList<Integer> {
	
	 public LampList(){
	 super();
	 }
	 
	 
	 public Integer getNext(int i){
		 Integer rep;
		 if (i==super.size()-1) rep=super.getFirst();
		 else  rep=super.get(i+1);		
		 return	rep;
	 }
	 
	 
	 public Integer getPrevious(int i){
		Integer rep;
		 if (i==0) rep=super.getLast();
		 else  rep=super.get(i-1);		
		 return	rep;
	 }
	 

}

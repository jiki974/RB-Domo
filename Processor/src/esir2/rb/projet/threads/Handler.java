package esir2.rb.projet.threads;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.text.TableView.TableRow;

import esir2.rb.projet.communicator.Communicator;
import esir2.rb.projet.processor.Processor;
import esir2.rb.projet.util.Util;

public class Handler implements Runnable {

	private boolean end=false;
	private Communicator comm;
	private volatile Thread handlerThread;
	private HashMap<String,Integer> actions=new HashMap<String,Integer>();
	private Processor proc;
	private String add;

	
	public Handler(String a, Communicator c,Processor p){
		add=a;
		proc=p;
		comm=c;	
	}

	public void run() {
		/*System.out.println("first run !");
	System.out.println("comm use : "+comm.getTechno());
	System.out.println("size of actions "+actions.size());*/

		Thread thisThread = Thread.currentThread();
		handlerThread=Thread.currentThread();



		while(handlerThread==thisThread){
			/*System.out.println("the handler "+this.toString());
			System.out.println("use the communicator : "+comm.toString());
			//System.out.println("in thread");*/
			Set gpadds= actions.keySet();
			Iterator it=gpadds.iterator();

			while(it.hasNext()){
				
				String actual=(String) it.next();
			//	System.out.println("actual gp in "+this.toString()+" = "+actual);
				//System.out.println("actual commu is "+comm.getTechno());
				if(comm.isDestination(add,actual)){
				
					System.out.println("group "+actual +" is destination");
					
					switch(actions.get(actual)){
					
				    
					
					case Util.PAUSE :
					proc.pauseSequence(add);
				    break;
				    
					case Util.RESUME :
					proc.resumeSequence(add);
					break;
					
					
	
					}
					
					
					
					
					comm.resetListener(add,actual);	
				}
			}




			try {
				thisThread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

		} // fin while

	}//fin run

	public void addActionToBePerformed(String addgp,int action) {

		if(!actions.containsKey(addgp))

		{
			comm.setListener(add,addgp);
			actions.put(addgp, action);
		}

	}

	public void startThread(){
		new Thread(this).start();
	}

	public void killThread(){
		handlerThread=null;
	}

  
	public String getState(String addgp) {
		if(!actions.containsKey(addgp))
		{
			
		comm.setListener(add,addgp);
		actions.put(addgp, -1);
		
			}
		String ret="0";
		
	
		ret=comm.getValue(add,addgp);
		
	
		comm.resetListener(add,addgp);
							
		
		return ret ;
	}
	

}

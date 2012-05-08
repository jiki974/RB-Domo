package Threads;

import Util.Soapcommunicator;
import Util.Util;
import View.IHM;

public class ReadState implements Runnable {
 String add;
 private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String local_address=Util.getLocalAdd();
	private Soapcommunicator sm=new Soapcommunicator(local_address,9000,namespace); // Création de notre Message
	
  public ReadState(String a){
	 add=a;
  }
		public void run() {
			// TODO Auto-generated method stub
		
			System.out.println("hello from retour knx");


			while (true){

				boolean ListenerLamp1=read(add,"0/3/0");
				boolean ListenerLamp2=read(add,"0/3/1");
				boolean ListenerLamp3=read(add,"0/3/2");
				boolean ListenerLamp4=read(add,"0/3/3");

				IHM.offAll();

				if(ListenerLamp1) IHM.allumeOnly1();
				if(ListenerLamp2) IHM.allumeOnly2();
				if(ListenerLamp3) IHM.allumeOnly3();
				if(ListenerLamp4) IHM.allumeOnly4();
				//System.out.println("youhouuuu");
				try {
					Thread.sleep(500); 
					//System.out.println("youhou");
				} catch (Exception e){
					System.out.println(e.getMessage());
				}
			}


		}

	


public boolean read(String add,String addgp){

	boolean response = false;
	
	sm.setMethod(webservice,"getState","add",add,"addgp",addgp);
	
	String rep=sm.getResponse().toString();

	if (rep.equals("1"))response=true;

	try {	sm.post();

	} catch (Exception e){
		System.out.println(e.getMessage());
	}

	return response;

}

}

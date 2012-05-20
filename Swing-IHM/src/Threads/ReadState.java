package Threads;

import Util.Soapcommunicator;
import Util.Util;
import View.IHM;

public class ReadState implements Runnable {



	private String add;
	private volatile Thread  readStateThread;

	private IHM myIHM;
	private Soapcommunicator mySoap;

	public ReadState(IHM i, Soapcommunicator s,String a){
		myIHM=i;
		mySoap=s;
		add=a;
	}


	public void run() {

		String[] valLamps;

		Thread thisThread= Thread.currentThread();

		readStateThread=Thread.currentThread();

		while (readStateThread==thisThread){
			valLamps=read(add,"0/3/",3).split(";");

			for(int i=0;i<valLamps.length;i++){

				if(valLamps[i].equals("1")) myIHM.turnOn(i); //Si la l'état de la lampe est égale à "1" on allume la lampe
				else myIHM.turnOff(i);

			}

			try {
				thisThread.sleep(Util.REFRESH_THREAD_READ_STATE);
			} catch (InterruptedException e) {
				System.err.print("Error during the ReadStateThread sleeping : ");
				System.err.println(e.getMessage());
			}

		}


	}


	/** Fonction permettant l'arret de la THREAD **/

	public void killThread(){
		readStateThread=null;
	}


	/** Fonction appelé afin de lire l'état d'un groupe **/

	public String read(String add,String addgp,int nb){

		String method="getState";
		mySoap.setMethod(method,"add",add,"addgp",addgp,"nb",String.valueOf(nb));//Indication de l'utilisation de la méthode getState

		String rep = "";// Initialisation de la string de réponse

		try {	
			mySoap.post();// on essaie de posté 
			rep=mySoap.getResponse();// et de récupérer l'état
		} 

		catch (Exception e){
			System.err.print("Error when posting  "+method+" :");
			System.out.println(e.getMessage());
		}

		return rep;

	}



}

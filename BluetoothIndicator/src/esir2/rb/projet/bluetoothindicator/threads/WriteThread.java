package esir2.rb.projet.bluetoothindicator.threads;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.microedition.io.StreamConnection;

import esir2.rb.projet.processor.Processor;

public class WriteThread implements Runnable {

	private StreamConnection streamConnection;
	private Processor proc;
	private Boolean end=false;
	private String add;
	private String addgp;
	private int nb;
	private OutputStream outStream;
	
	public WriteThread(StreamConnection s,Processor p,String a,String ag,int n){
		streamConnection=s;
		proc=p;
		add=a;
		addgp=ag;
		nb=n;
	}

	public void killThread(){
		end=true;
	}
	public void run() {
	
		 try {
			outStream = streamConnection.openOutputStream();
		} catch (IOException e) {
		System.err.println(e.getMessage());
		}
			 PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
		
     
		while(!end){

			String[] valLamps=proc.getState(add, addgp, nb).split(";");
			for(int i=0;i<valLamps.length;i++){

				if(valLamps[i].equals("1")) pWriter.write("h"+i); //Si la l'état de la lampe est égale à "1" on allume la lampe
				else pWriter.write("l"+i);
				pWriter.flush();
			}
			
			try{
				Thread.sleep(150);
			}catch (Exception e) {
			System.err.println(e.getMessage());
			}


		}
	}
	


}

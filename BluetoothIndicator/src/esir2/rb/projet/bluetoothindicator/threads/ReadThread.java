package esir2.rb.projet.bluetoothindicator.threads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

import esir2.rb.projet.processor.Processor;


public class ReadThread implements Runnable {

	StreamConnection streamConnection;
	InputStream inStream ;
	Processor proc;
	private  Boolean pause=false;
	String add;
	private boolean end=false;
	
	
	public ReadThread(StreamConnection s,Processor p,String a){
		streamConnection=s;
		proc=p;
		add=a;
	}

	public void killThread(){
		end=true;
	}
	public void run() {
		int i=0;
		try {
			inStream =streamConnection.openInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader   bReader=new BufferedReader(new InputStreamReader(inStream));



		while(!end){
			try {

				if(bReader.readLine().equals("T")){

					pause=!pause;
					if(pause){
						proc.pauseSequence(add);
					}
					else {
						proc.resumeSequence(add);
					}
				}

				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

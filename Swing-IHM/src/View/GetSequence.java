package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import Util.Soapcommunicator;
import Util.Util;


public class GetSequence implements ActionListener, Runnable{
	private static LampList<Integer>seq;
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	private IHM cheni;

	public GetSequence(IHM c){
		cheni=c;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String addCible=IHM.getChosenAdd();
		//si la techno est valide et si il y a une connection et si la séquence n'est pas en train d'être configurée
		if(cheni.isConnected(addCible)&&!IHM.getSeqSetState()&&IHM.getInvalidTechnoState()){
			cheni.startThreadSetup();
			IHM.setSeqIsSet(true);
			IHM.setEnded();
			cheni.stopThread();
			new Thread(this).start();
			address=Util.getLocalAdd();	
			addCible=IHM.getChosenAdd();
			IHM.setSeq("");
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"stopSequence","add",addCible);
			try {

				sm.post();
				System.out.println("post envoyé");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	@Override
	public void run() {

		String techno=IHM.getTechno();
		seq=new LampList<Integer>();
		//String sequence=null;

		IHM.offAll();

		address=Util.getLocalAdd();	
		String addCible=IHM.getChosenAdd();



		while(true){

			if(IHM.getNbLampSet()==4){

				System.out.println(IHM.getNbLampSet());
				IHM.resetNbLampSet();
				String s = IHM.getSequence();
				String[] sp=s.split("-");			

				for(int i=0;i<sp.length;i++) {

					System.out.println(sp[i]);
					seq.add(Integer.valueOf(sp[i]));
				}
				String Sequence=sp[0]+"-"+sp[1]+"-"+sp[2]+"-"+sp[3];
				System.out.println(seq);
				cheni.stopThreadSetup();			
				System.out.println(Sequence);
				IHM.setSeq(Sequence);
				Sequence=IHM.getSequence();


				sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
				sm.setMethod(webservice,"stopSequence","add",addCible);
				sm.setMethod(webservice,"startSequence","add",addCible,"nameseq",Sequence);
				IHM.setSeq(Sequence);		
				IHM.setSeqIsSet(false);
				cheni.startThreadChenillard();

				try {

					sm.post();
					System.out.println("post envoyé");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				for(int i=1;i<5;i++){
					IHM.setState(i, false);			
				}

				if(techno.equals("SIMU")){
					//cheni.startThreadVisuSwing();
					IHM.resetEnded();
					IHM.setPaused(false);
					System.out.println(IHM.getEnd());
				}	
				/*for(int x=1;x<5;x++){
				switch(x){
				case(1):if(chen.getSetState(1)==false)chen.allumeOnly1();
				case(2):if(chen.getSetState(2)==false)chen.allumeOnly2();
				case(3):if(chen.getSetState(3)==false)chen.allumeOnly3();
				case(4):if(chen.getSetState(4)==false)chen.allumeOnly4();
				default:break;
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			}*/		
				else{IHM.offAll();}

				System.out.println("Sequence="+Sequence);

			}
			try {
				Thread.sleep(500);


			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}





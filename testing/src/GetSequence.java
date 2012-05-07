import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;


public class GetSequence implements ActionListener, Runnable{
	private static LampList<Integer>seq;
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	private chen cheni;

	public GetSequence(chen c){
		cheni=c;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String addCible=chen.getChosenAdd();
		//si la techno est valide et si il y a une connection et si la séquence n'est pas en train d'être configurée
		if(cheni.isConnected(addCible)&&!chen.getSeqSetState()&&chen.getInvalidTechnoState()){
			cheni.startThreadSetup();
			chen.setSeqIsSet(true);
			chen.setEnded();
			cheni.stopThread();
			new Thread(this).start();
			address=Util.getLocalAdd();	
			addCible=chen.getChosenAdd();
			chen.setSeq("");
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

		String techno=chen.getTechno();
		seq=new LampList<Integer>();
		//String sequence=null;

		chen.offAll();

		address=Util.getLocalAdd();	
		String addCible=chen.getChosenAdd();



		while(true){

			if(chen.getNbLampSet()==4){

				System.out.println(chen.getNbLampSet());
				chen.resetNbLampSet();
				String s = chen.getSequence();
				String[] sp=s.split("-");			

				for(int i=0;i<sp.length;i++) {

					System.out.println(sp[i]);
					seq.add(Integer.valueOf(sp[i]));
				}
				String Sequence=sp[0]+"-"+sp[1]+"-"+sp[2]+"-"+sp[3];
				System.out.println(seq);
				cheni.stopThreadSetup();			
				System.out.println(Sequence);
				chen.setSeq(Sequence);
				Sequence=chen.getSequence();


				sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
				sm.setMethod(webservice,"stopSequence","add",addCible);
				sm.setMethod(webservice,"startSequence","add",addCible,"nameseq",Sequence);
				chen.setSeq(Sequence);		
				chen.setSeqIsSet(false);
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
					chen.setState(i, false);			
				}

				if(techno.equals("SIMU")){
					//cheni.startThreadVisuSwing();
					chen.resetEnded();
					chen.setPaused(false);
					System.out.println(chen.getEnd());
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
				else{chen.offAll();}

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





package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Util.Soapcommunicator;
import Util.Util;


public class ButtonsListener implements ActionListener {


	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String local_address=Util.getLocalAdd();
	private Soapcommunicator sm=new Soapcommunicator(local_address,9000,namespace); // Création de notre Message;	
	private JButton b;
	private String tech="";
	private String addr="";

	private IHM chenillard;




	public ButtonsListener(IHM i){
		chenillard=i;
	}
	public void actionPerformed(ActionEvent event) {
		addr=IHM.getChosenAdd();

		b=(JButton) event.getSource();

		if(b.equals(IHM.bConnect)) prepareToConnect();
		else if (b.equals(IHM.bPauseResume)) prepareToPauseResume();
		else if (b.equals(IHM.bDisconnect)) prepareToDisconnect();
		else if (b.equals(IHM.bStop)) prepareToStop();
		else if (b.equals(IHM.bStart)) prepareToStart();

		try {
			sm.post();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void prepareToStart(){
		if(IHM.getSeqSetState()==true){
			IHM.setSeqIsSet(false);
			chenillard.stopThreadSetup();

		}
		if (IHM.getInvalidTechnoState()){

			if(chenillard.isConnected(addr)){
				String techno=IHM.getTechno();


				IHM.setSeq("1-2-3-4");
				String Sequence=IHM.getSequence();
				if (techno.equals("SIMU")){chenillard.startThreadChenillard();}	



				sm.setMethod(webservice,"startSequence","add",addr,"nameseq",Sequence);
			}
		}

	}


	public void prepareToStop(){
		if(IHM.getSeqSetState()==true){
			IHM.setSeqIsSet(false);
			chenillard.stopThreadSetup();
		}


		if (IHM.getInvalidTechnoState()){//si la techno est valide
			String addCible=IHM.getChosenAdd();

			if(chenillard.isConnected(addCible)){
				IHM.setEnded();
				chenillard.stopThread();
			}

			sm.setMethod(webservice,"stopSequence","add",addr);

		}
	}


	public void prepareToDisconnect(){
		if(IHM.getSeqSetState()==true){
			IHM.setSeqIsSet(false);
			chenillard.stopThreadSetup();
		}

		IHM.setEnded();
		chenillard.stopThread();	
		sm.setMethod(webservice,"disconnect","add",addr);
	}


	public void prepareToPauseResume(){
		if(IHM.getSeqSetState()==true){
			IHM.setSeqIsSet(false);

		}
		IHM.setPaused(true);		
		sm.setMethod(webservice,"pauseSequence","add",addr);
	}


	public void prepareToConnect(){
		if (IHM.getChosenTechno().equals("KNX")){
			tech="1";	
		}
		else  if(IHM.getChosenTechno().equals("SIMU")){tech="0";}
		sm.setMethod(webservice,"connect","add",addr,"techno",tech);
	}

}

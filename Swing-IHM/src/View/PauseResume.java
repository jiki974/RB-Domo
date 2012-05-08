package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import Util.Soapcommunicator;
import Util.Util;


public class PauseResume implements ActionListener{

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="http://192.168.1.106";
	private IHM cheni;

	public PauseResume(IHM c){
		cheni=c;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		//éteinds la séquence setup si elle est en cours et réinitialise le button setup
		if(IHM.getSeqSetState()==true){
			IHM.setSeqIsSet(false);
			cheni.stopThreadSetup();

		}
		if (IHM.getInvalidTechnoState()){
			String addCible=IHM.getChosenAdd();
			if(cheni.isConnected(addCible)){
				boolean isPaused=IHM.isPaused();
				if(isPaused==false){
					cheni.pauseThread();
					IHM.setPaused(true);

					address=Util.getLocalAdd();	
					addCible=IHM.getChosenAdd();
					sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
					sm.setMethod(webservice,"pauseSequence","add",addCible);

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

				else{			

					cheni.resumeThread();
					IHM.setPaused(false);
					addCible=IHM.getChosenAdd();
					sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
					sm.setMethod(webservice,"resumeSequence","add",addCible);

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
		}
	}
}





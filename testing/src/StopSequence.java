import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class StopSequence implements ActionListener {

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="http://192.168.1.106";
	private chen cheni;

	public StopSequence(chen c){
		cheni=c;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {

		//éteinds la séquence setup si elle est en cours et réinitialise le button setup
		if(chen.getSeqSetState()==true){
			chen.setSeqIsSet(false);
			cheni.stopThreadSetup();

		}
		if (chen.getInvalidTechnoState()){//si la techno est valide
			String addCible=chen.getChosenAdd();
			if(cheni.isConnected(addCible)){
				chen.setEnded();
				cheni.stopThread();

			}
			address=Util.getLocalAdd();	
			addCible=chen.getChosenAdd();
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
}





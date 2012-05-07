import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class Disconnect implements ActionListener {

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	
	private chen cheni;
	
	public Disconnect(chen c){
			cheni=c;
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(chen.getSeqSetState()==true){
			chen.setSeqIsSet(false);
			cheni.stopThreadSetup();
			
		}
			
		chen.setEnded();
		cheni.stopThread();		
		address=Util.getLocalAdd();
		String addr=chen.getChosenAdd();
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"disconnect","add",addr);

		try {

			sm.post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}





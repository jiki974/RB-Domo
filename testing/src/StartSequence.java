import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;


public class StartSequence implements ActionListener {
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	private chen cheni;

	public StartSequence(chen c){
		cheni=c;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//éteinds la séquence setup si elle est en cours et réinitialise le button setup
		
		if(chen.getSeqSetState()==true){
			chen.setSeqIsSet(false);
			cheni.stopThreadSetup();

		}
		if (chen.getInvalidTechnoState()){
		String addCible=chen.getChosenAdd();
		if(cheni.isConnected(addCible)){
			String techno=chen.getTechno();
				
			
			chen.setSeq("1-2-3-4");
			String Sequence=chen.getSequence();
			if (techno.equals("SIMU")){cheni.startThreadChenillard();}	
			
			address=Util.getLocalAdd();	
			addCible=chen.getChosenAdd();			
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"startSequence","add",addCible,"nameseq",Sequence);
			
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
	}
}

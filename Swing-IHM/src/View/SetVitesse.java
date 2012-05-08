package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import Util.Soapcommunicator;
import Util.Util;


public class SetVitesse implements ActionListener {
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="http://192.168.1.106";
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		address=Util.getLocalAdd();	
		String addCible=IHM.getChosenAdd();
		int speedChosen=IHM.getChosenSpeed();
		String Speed;
					    
		if (speedChosen>500 && speedChosen<10000 ){
					Speed=""+speedChosen;
						System.out.println(Speed);
		}
		else{Speed="800";}
		System.out.println(speedChosen + "speedy"+ Speed);
		
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"setSequenceSpeed","add",addCible,"speed",Speed);
		
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

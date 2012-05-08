package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.ksoap2.serialization.SoapPrimitive;

import Util.Soapcommunicator;
import Util.Util;


public class GetVitesse implements ActionListener {		
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address=Util.getLocalAdd();	
	private Soapcommunicator sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		String addr=IHM.getChosenAdd();
		
		sm.setMethod(webservice,"getSequenceSpeed","add",addr);
		SoapPrimitive msg= new SoapPrimitive(webservice, address, namespace);
		String response=sm.getResponse().toString();	
		System.out.println("vitesse= "+sm.getResponse());
		try {
           
			sm.post();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}

}

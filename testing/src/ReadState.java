import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;


public class ReadState implements ActionListener {
	
	
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="http://192.168.1.106";
	private Boolean [] state={null,null,null,null};
	private int address_nb=0;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		address=Util.getLocalAdd();		
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"readState","address_nb","0");
		SoapPrimitive msg= new SoapPrimitive(webservice, address, namespace);
		String response=sm.getResponse().toString();
		System.out.println(response);
		System.out.println("blbalbabl"+sm.getResponse());
	
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

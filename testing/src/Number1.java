import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;


public class Number1 implements ActionListener{
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		
		if (chen.getSeqSetState()==true&&!chen.getSetState(1)){
		//au niveau de swing
		chen.setState(1, true);
		chen.setNum("1");
		String num=chen.getNum();
		chen.setSequence(num);
		String seq=chen.getSequence();
		System.out.println(seq);
		chen.allumeOnly1();
		chen.setNbLampSet();
		int nb=chen.getNbLampSet();
		System.out.println("nb lampe selectionné "+nb);
		
		//au niveau de knx
		address=Util.getLocalAdd();	
		String addCible=chen.getChosenAdd();
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"writeOn","add",addCible,"lampe","1");
				
		
		
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

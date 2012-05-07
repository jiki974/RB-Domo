import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import org.xmlpull.v1.XmlPullParserException;

public class Connect implements ActionListener {
	
	

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String addressLocal="";
	private String tech="SIMU";
	private chen cheni;
	

	public Connect(chen chen) {
		cheni=chen;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		addressLocal=Util.getLocalAdd();	
	
		if (chen.getChosenTechno().equals("KNX")){
			tech="1";
			
		}
		else {tech="0";}
	
		String addressChosen= chen.getChosenAdd();
		System.out.println("retour : "+ tech + " : " +addressChosen);
		sm=new Soapcommunicator(addressLocal,9000,namespace); // Création de notre Message
		System.out.println("addresschosen: "+addressChosen);
		System.out.println("type = "+tech);
		sm.setMethod(webservice,"connect","add",addressChosen,"techno","1");
						
		try {

			sm.post();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}





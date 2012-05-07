import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;


public class Number2 implements ActionListener{
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (chen.getSeqSetState()==true&&!chen.getSetState(2)){
			//au niveau de swing
			chen.setState(2, true);
		chen.setNum("2");
		String num=chen.getNum();
		chen.setSequence(num);
		String seq=chen.getSequence();
		System.out.println(seq);
		chen.allumeOnly2();
		chen.setNbLampSet();
		int nb=chen.getNbLampSet();
		System.out.println("nb lampe selectionné "+nb);
		
		//au niveau de knx
				address=Util.getLocalAdd();	
				String addCible=chen.getChosenAdd();
				sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
				sm.setMethod(webservice,"writeOn","add",addCible,"lampe","2");
		
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

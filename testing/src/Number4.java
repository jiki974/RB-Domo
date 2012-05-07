import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;


public class Number4 implements ActionListener{
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	@Override
	
	public void actionPerformed(ActionEvent arg0) {
		
		if (chen.getSeqSetState()==true&&!chen.getSetState(4)){
			//au niveau de swing
			chen.setState(4, true);
			//au niveau de swing
			chen.setNum("4");
			String num=chen.getNum();
			chen.setSequence(num);
			String seq=chen.getSequence();
			System.out.println(seq);
			chen.allumeOnly4();
			chen.setNbLampSet();
			int nb=chen.getNbLampSet();
			System.out.println("nb lampe selectionn� "+nb);
			//au niveau de knx
			address=Util.getLocalAdd();	
			String addCible=chen.getChosenAdd();
			sm=new Soapcommunicator(address,9000,namespace); // Cr�ation de notre Message
			sm.setMethod(webservice,"writeOn","add",addCible,"lampe","4");
		
		try {

			sm.post();
			System.out.println("post envoy�");
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

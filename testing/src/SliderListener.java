import java.io.IOException;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.xmlpull.v1.XmlPullParserException;


public class SliderListener implements ChangeListener{
	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address="";
	private chen cheni;

	public SliderListener(chen c){
		cheni=c;
	}
	@Override
	public void stateChanged(ChangeEvent evt) {
		String addCible=chen.getChosenAdd();
		
			address="http://"+Util.getLocal();
			JSlider source = (JSlider)evt.getSource();
			int Value  = (int)source.getValue();
			if (!source.getValueIsAdjusting()||Value!=800) {
				 Value  = (int)source.getValue();
				chen.setChosenSpeed(Value);
			}

			int speedChosen=chen.getChosenSpeed();		
			addCible=chen.getChosenAdd();

			String Speed;

			if (speedChosen>=500 && speedChosen<=1500){
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



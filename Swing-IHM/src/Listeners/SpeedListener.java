package Listeners;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Util.Soapcommunicator;
import Util.Util;
import View.IHM;

public class SpeedListener implements ChangeListener {

	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private Soapcommunicator sm=new Soapcommunicator(Util.getLocalAdd()+":"+Util.PORT,namespace,webservice); // Création de notre Message
	private IHM myIHM;

	public SpeedListener(IHM i){
		myIHM=i;
	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		JSlider jslid=(JSlider) ev.getSource();
		String speed=String.valueOf(jslid.getValue());
		//System.out.println(speed);

		sm.setMethod("setSequenceSpeed","add",myIHM.getTechnoAdd(),"speed",speed);

		try {	sm.post();

		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}

}

package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Threads.Setup;
import Util.Soapcommunicator;
import Util.Util;

public class SetupListener implements ActionListener {

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address = Util.getLocalAdd();
	private static Boolean inSetupMode=false;
	
	public void actionPerformed(ActionEvent e) {
		
	inSetupMode=true;
	/*Thread setupTread=new Thread(new Setup());
	setupTread.start();*/
	}
	
	public static Boolean inSetupMode(){
		return inSetupMode;
	}

	

}

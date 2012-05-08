package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import Util.Soapcommunicator;
import Util.Util;
import View.IHM;

public class LampesListener implements ActionListener {

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address = Util.getLocalAdd();	
	private static ImageIcon iconLedOn = createImageIcon("/images/bulb_yellow.png",	"a pretty but meaningless splat");
	private JCheckBox c;
	public void actionPerformed(ActionEvent e) {

		c=(JCheckBox) e.getSource(); /** Récupération de la source et casting en checkbox **/


		int i=0;
	

		if (c.equals(IHM.lampe1)){
			i=1;
		}
		else if (c.equals(IHM.lampe2)){
			i=2;
		}
		else if (c.equals(IHM.lampe3)){
			i=3;
		}
		else if (c.equals(IHM.lampe4)){
			i=4;
		}

		String s=String.valueOf(i);

		if (IHM.getSeqSetState()==true&&!IHM.getSetState(i)){
			//au niveau de swing

			IHM.setState(i, true);
			IHM.setNum(s);
			
			String num=IHM.getNum();
			IHM.setSequence(num);
			String seq=IHM.getSequence();
			System.out.println(seq);
            c.setIcon(iconLedOn);
			IHM.setNbLampSet();
			int nb=IHM.getNbLampSet();


			System.out.println("nb lampe selectionné "+nb);

			//au niveau de knx

			String addCible=IHM.getChosenAdd();
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"writeOn","add",addCible,"lampe",s);



			try {
				sm.post();
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}

		}	


	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = IHM.class.getResource(path);
		System.out.println(imgURL);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}

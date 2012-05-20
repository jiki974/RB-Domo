package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Components.LampeView;
import View.IHM;

public class LampesListener implements ActionListener {

	private LampeView lampe;
	private IHM myIHM;
	
	public LampesListener(IHM i){
		myIHM=i;
	}
	
	
	public void actionPerformed(ActionEvent e) {
	

		lampe=(LampeView) e.getSource(); /** Récupération de la source et casting en checkbox **/
		
		int i=0;
		switch (lampe.getId()){

		case(1): i=1;
		break;
		case(2): i=2; 
		break;
		case(3): i=3;
		break;
		case(4): i=4; 
		break;
		}
		
		if(SetupListener.inSetupMode()){
			System.out.println("lampe "+i);

			String s=String.valueOf(i);
		}

		
		
		
/*
		if (IHM_OLD.getSeqSetState()==true&&!IHM_OLD.getSetState(i)){
			//au niveau de swing

			IHM_OLD.setState(i, true);
			IHM_OLD.setNum(s);
			
			String num=IHM_OLD.getNum();
			IHM_OLD.setSequence(num);
			String seq=IHM_OLD.getSequence();
			System.out.println(seq);
			lampe.turnOn();

			IHM_OLD.setNbLampSet();
			int nb=IHM_OLD.getNbLampSet();


			System.out.println("nb lampe selectionné "+nb);

			//au niveau de knx

			String addCible=IHM_OLD.getChosenAdd();
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"writeOn","add",addCible,"lampe",s);



			try {
				sm.post();
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}

		}	
*/

	}


	

}

package Control;

import javax.swing.JOptionPane;

import Util.Soapcommunicator;
import Util.Util;
import View.IHM;

public class Sequence {

	private  Soapcommunicator sm=new Soapcommunicator(null,Util.NAMESPACE,Util.WEBSERVICE); // Création de notre Message
    private  String[] technos={"SIMU","KNX"};
    private  Boolean pause=false;
    private IHM myIHM;
    
    public Sequence(IHM i){
    	myIHM=i;
    }
	public  void start(String addCible,String nameseq){

		String method="startSequence";
		String add = "http://"+myIHM.getWsAdd()+":"+Util.PORT;

		if(Util.urlIsReachable(add, 200)){

			sm.changeAdd(add);
			sm.setMethod(method,"add",addCible,"nameseq",nameseq);




			try {
				sm.post();

			//	IHM.changeConnectText("Connected at "+addCible+" in "+technos[Integer.valueOf(techno)]+" mode");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Method : "+method+"doesn't exist !", e.getMessage(),2 );
			}

		}

		else{
			JOptionPane.showMessageDialog(null,"Webservice is unreachable !","Error",2 );
		}
	}

	public  void stop(String addCible) {
		String method="stopSequence";
		String add = "http://"+myIHM.getWsAdd()+":"+Util.PORT;
		if(Util.urlIsReachable(add, 200)){

			sm.changeAdd(add);
			sm.setMethod(method,"add",addCible);




			try {
				sm.post();

			//	IHM.changeConnectText("Connected at "+addCible+" in "+technos[Integer.valueOf(techno)]+" mode");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Method : "+method+"doesn't exist !", e.getMessage(),2 );
			}

		}

		else{
			JOptionPane.showMessageDialog(null,"Webservice is unreachable !","Error",2 );
		}
		
	}
	

	public  void pauseResume(String addCible) {
	
		pause=!pause;
		
		String method;
		 
		if(pause){
			method="pauseSequence";
			myIHM.inPause();
		}
		else 	{
			method="resumeSequence";
			myIHM.inResume();
		}
		
		
		
		String add = "http://"+myIHM.getWsAdd()+":"+Util.PORT;
		if(Util.urlIsReachable(add, 200)){

			sm.changeAdd(add);
			sm.setMethod(method,"add",addCible);




			try {
				sm.post();

			//	IHM.changeConnectText("Connected at "+addCible+" in "+technos[Integer.valueOf(techno)]+" mode");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Method : "+method+"doesn't exist !", e.getMessage(),2 );
			}

		}

		else{
			JOptionPane.showMessageDialog(null,"Webservice is unreachable !","Error",2 );
		}
		
	}

}

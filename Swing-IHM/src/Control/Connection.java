package Control;

import javax.swing.JOptionPane;

import Threads.ReadState;
import Util.Soapcommunicator;
import Util.Util;
import View.IHM;

public class Connection {

	private Soapcommunicator sm=new Soapcommunicator(null,Util.NAMESPACE,Util.WEBSERVICE); // Création de notre Message
	private Thread threadReadState;
	protected ReadState rState;
    private String[] technos={"SIMU","KNX"};
    private IHM myIHM;
    
    public Connection(IHM i){
    	myIHM=i;	
    }
    
	public  void open(String addCible,String techno){
		
		String method="connect";
		String add = "http://"+myIHM.getWsAdd()+":"+Util.PORT;

		sm.changeAdd(add);
		rState=new ReadState(myIHM,sm,addCible);

	if(Util.urlIsReachable(add, 200)){

	
			sm.setMethod(method,"add",addCible,"techno",techno);

	
			
			try {
				sm.post();	
				threadReadState=new  Thread(rState);
				threadReadState.setName("ReadState Thread");
				threadReadState.start();
		
				myIHM.changeConnectText("Connected at "+addCible+" in "+technos[Integer.valueOf(techno)]+" mode");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Method : "+method+" doesn't exist !", e.getMessage(),2 );
			}
		
		}
		
		else{
			JOptionPane.showMessageDialog(null,"Webservice is unreachable !","Error",2 );
		}
	}

	public void close(String addCible){
		rState.killThread();
		myIHM.allOff();
	
		String method="disconnect";
		String add = "http://"+myIHM.getWsAdd()+":"+Util.PORT;
		if(Util.urlIsReachable(add, 200)){
		sm.changeAdd(add);
		sm.setMethod(method,"add",addCible);
		try {
			sm.post();
			myIHM.changeConnectText("No connection");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Method : "+method+" doesn't exist !", e.getMessage(),2 );
		}
	}
	else{
		JOptionPane.showMessageDialog(null,"Webservice is unreachable !","Error",2 );
	}
	
}
	

}

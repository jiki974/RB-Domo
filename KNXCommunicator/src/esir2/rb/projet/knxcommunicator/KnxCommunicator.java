package esir2.rb.projet.knxcommunicator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.exception.KNXFormatException;
import esir2.rb.projet.communicator.Communicator;



public class KnxCommunicator  implements Communicator,BundleActivator{


	
	private ServiceRegistration sr;
	private boolean connected=false;
	private BundleContext bundlecontext;
	private ServiceReference refs;


	private HashMap<String,Connection> connections=new HashMap<String,Connection>();
	
	public void start(BundleContext bc) throws Exception {
		bundlecontext=bc;
		Dictionary dict= new Properties();
		dict.put("type", "KNX");
		sr = bc.registerService(Communicator.class.getName(), this, dict);	
		System.out.println("Knx Communicator is available !");
		
	}


	public void stop(BundleContext bc) throws Exception {
	sr.unregister();
	System.out.println("stop");
	//Fermer toutes les connexions
	}


	public boolean openConnection(String ipadd) throws Exception{

			if(!connections.containsKey(ipadd)){
				Connection conn=new Connection(ipadd);
				connections.put(ipadd,conn);
				connected=conn.isConnected();
			}
			return connected;
	}


	public void closeConnection(String ipadd) {
		if(connections.containsKey(ipadd)){
			connections.get(ipadd).close();
			connections.remove(ipadd);
		}
	
	}


	public void writeOn(String ipadd, int address_nb) throws KNXFormatException, KNXException  {
		if(connections.containsKey(ipadd))	connections.get(ipadd).writeOn(address_nb);
	}


	public void writeOff(String ipadd,int address_nb) throws KNXFormatException, KNXException{
		if(connections.containsKey(ipadd))	connections.get(ipadd).writeOff(address_nb);
	}




	public void allOff(String ipadd) throws KNXFormatException, KNXException {
		if(connections.containsKey(ipadd))	connections.get(ipadd).allOff();
	}





	public boolean isConnected(String ipadd) {
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).isConnected();
		return res;
	}

   

	@Override
	public String getTechno() {
		// TODO Auto-generated method stub
		return "KNX";
	}


	@Override
	public void setListener(String ipadd, String gpadd) {
		System.out.println("set listener on "+gpadd);
		if(connections.containsKey(ipadd)) connections.get(ipadd).setListener(gpadd);
	
	}
	
	
	public void removeListener(String ipadd, String gpadd){
		System.out.println("remove listener on "+gpadd);
		if(connections.containsKey(ipadd)) connections.get(ipadd).removeListener(gpadd);

	}
	
	public boolean  isSource(String ipadd,String gpadd){
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).isSource(gpadd);
		return res;
	}
	
	public boolean isDestination(String ipadd,String gpadd){
		//System.out.println("ipadd in knxcomm is :"+ipadd);
		//System.out.println("gpadd in knxcomm is :"+gpadd);
		boolean res=false;
		if(connections.containsKey(ipadd))	res=connections.get(ipadd).isDestination(gpadd);
	
		//System.out.println("res for is destination in knx comm is : "+res);
		return res;
	}	
	
	public void resetListener(String ipadd, String gpadd){
		if(connections.containsKey(ipadd)) connections.get(ipadd).resetListener(gpadd);
	}
	
	public boolean readState(String ipadd,int address_nb) throws KNXFormatException, KNXException{
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).readState(address_nb);
		return res;
	}


	@Override
	public String getValue(String ipadd, String gpadd,int nb) {
		String res="";
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).getValue(gpadd,nb);
		return res;
	}


	
	
	
	
	
}

	



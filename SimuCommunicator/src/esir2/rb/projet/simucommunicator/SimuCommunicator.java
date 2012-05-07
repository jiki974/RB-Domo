package esir2.rb.projet.simucommunicator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import esir2.rb.projet.communicator.Communicator;

public class SimuCommunicator implements Communicator, BundleActivator {

	private ServiceRegistration m_sr;	


	private boolean connected=false;
	private HashMap<String,Connection> connections=new HashMap<String,Connection>();

	public void start(BundleContext bc) throws Exception {

		Dictionary dict= new Properties();
		dict.put("type", "SIMU");
		m_sr = bc.registerService(Communicator.class.getName(), this, dict);
		System.out.println("Simulation Communicator is available !");	
	}


	public void stop(BundleContext bc) throws Exception {
		m_sr.unregister();
	}


	public boolean openConnection(String ipadd) {

		if(!connections.containsKey(ipadd)){
			Connection conn=new Connection(ipadd);
			connections.put(ipadd, conn);
			connected=conn.isConnected();
		}

		return connected;
	}


	public void closeConnection(String ipadd) {

		if(connections.containsKey(ipadd)) connections.remove(ipadd);
		connected=false;
		System.out.println("Simulation Communicator is disconnected !");
	}


	public void writeOn(String ipadd, int address_nb)  {
		if(connections.containsKey(ipadd)) connections.get(ipadd).writeOn(address_nb);
	}

	public void writeOff(String ipadd,int address_nb) {
		if(connections.containsKey(ipadd)) connections.get(ipadd).writeOff(address_nb);
	}

	public boolean readState(String ipadd,int address_nb){
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).readState(address_nb);
		return res;
	}


	public void allOff(String ipadd) {
		if(connections.containsKey(ipadd)) {
			for(int i=1;i<5;i++) connections.get(ipadd).writeOff(i);	
		}
	}


	public boolean isConnected(String ipadd) {
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).isConnected();
		return res;
	}


	@Override
	public String getTechno() {
		// TODO Auto-generated method stub
		return "SIMULATION";
	}


	public void setListener(String ipadd,String gpadd) {
		if(connections.containsKey(ipadd)) connections.get(ipadd).setListener(gpadd);
	}


	public void removeListener(String ipadd,String gpadd) {
		if(connections.containsKey(ipadd)) connections.get(ipadd).removeListener(gpadd);

	}

	public boolean  isSource(String ipadd,String gpadd){
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).isSource(gpadd);
		return res;
	}

	public boolean isDestination(String ipadd,String gpadd){
		boolean res=false;
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).isDestination(gpadd);
		return res;
	}	

	public void resetListener(String ipadd,String gpadd){
		if(connections.containsKey(ipadd)) connections.get(ipadd).resetListener(gpadd);	
	}


	@Override
	public String getValue(String ipadd, String gpadd) {
		String res="";
		if(connections.containsKey(ipadd)) res=connections.get(ipadd).getValue(gpadd);
		return res;

	}




}

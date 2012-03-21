package esir2.rb.projet.simucommunicator;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import esir2.rb.projet.communicator.Communicator;

public class SimuCommunicator implements Communicator, BundleActivator {

	private ServiceRegistration m_sr;	
	private Boolean[] lampsON= {false,false,false,false};
	private boolean connected=false;
	

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
	connected=true;
	System.out.println("Connected at " + ipadd +" !");
	return connected;	
	}


	public void closeConnection() {
	connected=false;
	System.out.println("Simulation Communicator is disconnected !");
	}
	
	
	public void writeOn(int address_nb)  {

		 System.out.println("Lamp "+address_nb+" is on");
		 lampsON[address_nb-1]=true;
		 }
		 
		 public void writeOff(int address_nb) {
	     System.out.println("Lamp "+address_nb+" is off");
		 lampsON[address_nb-1]=false;
		 }
		 
		 public boolean readState(int address_nb){
		 return  lampsON[address_nb]; 
		 }


	public void allOff() {
	for(int i=1;i<5;i++) this.writeOff(i);	
	}
	

	public boolean isConnected() {
	return connected;
	}


	@Override
	public String getTechno() {
		// TODO Auto-generated method stub
		return "SIMULATION";
	}
	
}

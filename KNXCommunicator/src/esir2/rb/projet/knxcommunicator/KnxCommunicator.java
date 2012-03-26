package esir2.rb.projet.knxcommunicator;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.exception.KNXFormatException;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;
import esir2.rb.projet.communicator.Communicator;
import esir2.rb.projet.processor.Processor;


public class KnxCommunicator  implements Communicator,BundleActivator{

	private String addGp="0/1/";
	private KNXNetworkLinkIP netLinkIp;
	private ProcessCommunicator pc;
	private ServiceRegistration sr;
	private boolean connected=false;
	private ButtonListener bl;
	
	private Processor process;
	
	private ServiceReference refs;
	private Thread buttonListenerThread;
	private CatchProcessor cProc;
	
	private boolean end=false;
	
	
	
	public void start(BundleContext bc) throws Exception {

		Dictionary dict= new Properties();
		dict.put("type", "KNX");
		sr = bc.registerService(Communicator.class.getName(), this, dict);	
		System.out.println("Knx Communicator is available !");
		//CatchProcessor cProc=new CatchProcessor(bc);
		//Thread t=new Thread(cProc);
		//t.start();
	}


	public void stop(BundleContext bc) throws Exception {
	sr.unregister();
	System.out.println("stop");
	}


	public boolean openConnection(String ipadd) throws Exception{
		
		InetSocketAddress dest=new InetSocketAddress(InetAddress.getByName(ipadd),KNXnetIPConnection.IP_PORT);
		
		netLinkIp = new KNXNetworkLinkIP(KNXNetworkLinkIP.TUNNEL,getLocalAddress(),dest,false, new TPSettings(false));
		
		if(netLinkIp.isOpen()) {
			connected=true;
			//process=cProc.getProcessor();
			//bl=new ButtonListener(process);// Déclaration du processus de recherche de Communicator
			//netLinkIp.addLinkListener(bl);
		}
		try {
			pc = new ProcessCommunicatorImpl(netLinkIp);
		} catch (Exception e) {
			connected=false;
			System.out.println(e.getClass());
		}
     return connected;
	}


	public void closeConnection() {
		netLinkIp.close();
		connected=false;
	}


	public void writeOn(int address_nb) throws KNXFormatException, KNXException  {
		pc.write(createGp(address_nb),true);
	}


	public void writeOff(int address_nb) throws KNXFormatException, KNXException{
		pc.write(createGp(address_nb),false);//En mode normal
	}


	public boolean readState(int address_nb) throws KNXFormatException, KNXException{

		return pc.readBool(createGp(address_nb));
	}


	public void allOff() throws KNXFormatException, KNXException{
		for(int i=1;i<=4;i++) this.writeOff(i);
	}
	public GroupAddress createGp(int i) throws KNXFormatException{

		return new GroupAddress(addGp+(i-1));//Retourne une addresse de groupe
	}

	public void setAddGp(String add){
		this.addGp=add; 
	}




	public static InetSocketAddress getLocalAddress() throws SocketException{ 
		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); //Récupération de toute les interfaces
		Boolean oncetime=false;
		String add="";	

		while (e.hasMoreElements()){ //Tant qu'il y a une interface

			Enumeration<InetAddress> i = e.nextElement().getInetAddresses(); //On récupère les InetAddress de cette interface
			while (i.hasMoreElements()){ //Tant qu'il y a une InetAddresse
				InetAddress a = i.nextElement(); //on recupère l'inetaddress
				if(a.isSiteLocalAddress() && !oncetime){//Si c'est une inet address de type local address
					oncetime=true;
					add=a.getHostName();
				}
			}
		}
		return new InetSocketAddress(add, 0); //On retourne L'InetSocketAddress
	}


	public boolean isConnected() {
		return connected;
	}
/*
public void run() {
		
		// TODO Auto-generated method stub
		while (!end) {
			ServiceReference refs[];
			try {
				refs = context.getAllServiceReferences(Processor.class.getName(),null);
				
				if (refs != null && refs.length != 0) {
					
					for (ServiceReference servRef : refs) {
						process = (Processor) context.getService(servRef);

										}
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
*/


	@Override
	public String getTechno() {
		// TODO Auto-generated method stub
		return "KNX";
	}	
}

	



package esir2.rb.projet.knxcommunicator;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.exception.KNXFormatException;
import tuwien.auto.calimero.exception.KNXTimeoutException;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXLinkClosedException;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

public class Connection {

	private String addGp="0/1/";
	private HashMap<String,KNXListener> listeners=new HashMap<String,KNXListener>();
	private KNXNetworkLinkIP netLinkIp;
	private ProcessCommunicator pc;
	private boolean connected=false;
	private String add;

	/**
	 * Constructeur d'un connection
	 * @param ipadd indique l'adresse ip
	 */
	public Connection(String ipadd) throws Exception {
		add=ipadd;
		InetSocketAddress dest=new InetSocketAddress(InetAddress.getByName(ipadd),KNXnetIPConnection.IP_PORT);
		netLinkIp = new KNXNetworkLinkIP(KNXNetworkLinkIP.TUNNEL,getLocalAddress(),dest,false, new TPSettings(false));
		if(netLinkIp.isOpen()) {
			connected=true;
		}
		
		try {
			pc = new ProcessCommunicatorImpl(netLinkIp);
		} catch (Exception e) {
			connected=false;
			System.out.println(e.getClass());
		}
		
	}

    public void close(){
    	netLinkIp.close();
		connected=false;
    }
	public boolean isConnected(){
		return connected;
	}

	/**
	 * Fonction permettant l'allumage d'une lampe
	 * @param address_nb indique l'adresse de la lampe
	 * @throws KNXFormatException 
	 * @throws KNXLinkClosedException 
	 * @throws KNXTimeoutException 
	 */
	public void writeOn(int address_nb) throws KNXTimeoutException, KNXLinkClosedException, KNXFormatException  {
	
		pc.write(createGp(address_nb),true);
	}

	/**
	 * Fonction permettant d'éteindre une lampe
	 * @param address_nb
	 * @throws KNXFormatException 
	 * @throws KNXLinkClosedException 
	 * @throws KNXTimeoutException 
	 */
	public void writeOff(int address_nb) throws KNXTimeoutException, KNXLinkClosedException, KNXFormatException {
		pc.write(createGp(address_nb),false);//En mode normal
	}

	/**
	 * Fonction permettant d'éteindre toutes les lampes
	 */
	public void allOff() throws KNXFormatException, KNXException{
		for(int i=1;i<=4;i++) this.writeOff(i);
	}

	/**
	 * Fonction permettant de lire l'état d'une lampe
	 * @param address_nb indique l'adresse de la lampe
	 * @return l'etat d'une lampe
	 */
		public boolean readState(int address_nb) throws KNXFormatException, KNXException{

		return pc.readBool(createGp(address_nb));
		}



	/**
	 * Fonction permettant d'affecter un listener
	 * @param gpadd indique une adresse de groupe
	 */
	public void setListener(String gpadd) {

		if(!listeners.containsKey(gpadd)){// Si le listener n'existe pas déja
			KNXListener list=new KNXListener(gpadd);
			netLinkIp.addLinkListener(list);
			listeners.put(gpadd,list);//on ajoute le listener dans notre hashmap

		}
	}


	/**
	 * Fonction permettant la suppression de listener
	 * @param gpadd indique une adresse de groupe
	 */
	public void removeListener(String gpadd){
		if(listeners.containsKey(gpadd)){	
			netLinkIp.removeLinkListener(listeners.get(gpadd));
			listeners.remove(gpadd);
			}
			

	}

	/**
	 * Fonction permettant de connaitre si l'adresse de groupe est une adresse de source
	 * @param gpadd indique une adresse de groupe
	 * @return un boolean indiquant si l'adresse et une adresse source
	 */
	public boolean  isSource(String gpadd){
		boolean res=false;
		if(listeners.containsKey(gpadd)){	
		res=listeners.get(gpadd).isSource();
		}
		return res;
	}

	/**
	 * Fonction permettant de connaitre si l'adresse de groupe est une adresse de destination
	 * @param gpadd indique une adresse de groupe
	 * @return un boolean indiquant si l'adresse et une adresse de destination
	 */
	public boolean isDestination(String gpadd){
		
		boolean res=false;
		if(listeners.containsKey(gpadd)){	
		res=listeners.get(gpadd).isDestination();}
		return res;
	}	


	/**
	 * Fonction permettant de mettre à 0 
	 * @param gpadd indique une adresse de groupe
	 */
	public void resetListener(String gpadd){
		listeners.get(gpadd).reset();	
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

	
	public GroupAddress createGp(int i) throws KNXFormatException{

		return new GroupAddress(addGp+(i-1));//Retourne une addresse de groupe
	}

	public void setAddGp(String add){
		this.addGp=add; 
	}

	public String getValue(String gpadd) {
		String res="";
		if(listeners.containsKey(gpadd)){	
		res=listeners.get(gpadd).getValue();}
		return res;
		
	}
	
	

}

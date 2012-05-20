package Util;



import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;

import View.IHM;

public class Util {
	
	/** Déclaration des variables **/
	
	public final static String ADDRESS_PLATFORM="http://localhost"; // Addresse de la plateforme simulé
	public final static int PORT=9000; 
	public final static int REFRESH_THREAD_SEQUENCE=800;
	public final static int REFRESH_THREAD_READ_STATE=100;
	public final static int SIMU=0;
	public final static int KNX=1;
	public final static int BACNET=2;
	

	public final  static String WEBSERVICE="wsandroid";
	public final static String NAMESPACE="http://wsandroid.projet.rb.esir2/";
	private static Soapcommunicator sm;	

	
	/** Fonction retournant l'addresse où le webservice ws sera accessible **/
	
	public static  String getLocaWsAddress(int port,String ws) {
		String ret ="";
		try {
			 ret="http://"+InetAddress.getLocalHost().getHostAddress()+":"+port+"/"+ws; //Utilisation d'InetAddress.getlocal+nom du webservice
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static  String getLocal() {
		String ret ="";
		try {
			 ret=InetAddress.getLocalHost().getHostAddress(); //Utilisation d'InetAddress.getlocal+nom du webservice
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/** Fonction qui test la connection avec la plateforme distante **/

	public static boolean urlIsReachable(String url,int conntimeout) {
		HttpURLConnection connection;
		
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();//on ouvre une connection
			connection.setConnectTimeout(conntimeout);
			connection.setRequestMethod("HEAD");//On récupére l'entête http
			String server =connection.getHeaderField("Server");
			connection.disconnect();
			return (server.contains("Jetty"));// on retourne le résultat de la comparaison avec un http OK (complémenter)
		} catch (Exception e) {
		
			return false;//On renvoie true pour indique que le service est injoignable
		}
	}
	
	public static  String getLocalAdd() {
		String ret ="";
		try {
			ret="http://"+InetAddress.getLocalHost().getHostAddress(); //Utilisation d'InetAddress.getlocal+nom du webservice
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
		//ret="http://192.16.1.5";
		return ret;
	}
	
	
	public  static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = IHM.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	
	public static boolean isConnected(String add){
	
		boolean response = false;
		
		sm=new Soapcommunicator(getLocalAdd()+":"+PORT,NAMESPACE,WEBSERVICE); // Création de notre Message
		//sm.setMethod("isConnected","add",add);
		
	sm.setMethod("isConnected","add",add);
		

		//sm.setMethod("isConnected","add",add);

		try {			
				
			
		
			sm.post();

			response=Boolean.valueOf(sm.getResponse().toString());
			
		
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		return response;
	}
	
	
	
	// fonction compare si la technologie sélectionnée dans le volet déroulant correspond avec celle qui est connecté		

/*	public static boolean isConnectedToTech(String add,String tech){

		Boolean isConected=isConnected(add);		
		String techno=IHM.getChosenTechno();
		if (techno.equals(tech)&&isConected)return true;
		else{return false;}

	}
	*/
	
}

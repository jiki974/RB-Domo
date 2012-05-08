package Util;



import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Util {
	
	/** Déclaration des variables **/
	
	public final static String ADDRESS_PLATFORM="http://localhost"; // Addresse de la plateforme simulé
	public final static int PORT=9000; 
	public final static int REFRESH_THREAD_SEARCH_COMMUNICATORS=5000;
	public final static int REFRESH_THREAD_SEQUENCE=800;
	
	public final static int SIMU=0;
	public final static int KNX=1;
	public final static int BACNET=2;

	
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
			int r=connection.getResponseCode();
			connection.disconnect();
			return (r == HttpURLConnection.HTTP_OK || r==HttpURLConnection.HTTP_NOT_FOUND);// on retourne le résultat de la comparaison avec un http OK (complémenter)
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
	
	
	
}

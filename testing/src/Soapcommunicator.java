/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author HOAREAU Jacky
 */
public class Soapcommunicator {

	/** Déclaration des variables **/

	private String address,namespace,webservice;
	private int port;
	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

	public Soapcommunicator(String address,int port,String namespace){
		this.address=address;
		this.port=port;
		this.namespace=namespace;
	}

	/** Fonction indiquant quel methode à appler via SOAP **/

	public void setMethod(String webservice,String method){
		this.webservice=webservice;
		SoapObject request = new SoapObject(this.namespace, method);//Création de la requête

		envelope.setOutputSoapObject(request);//On met notre requete dans l'envelope SOAP
	}
	public void setMethod(String webservice,String method,String param,String value){
		this.webservice=webservice;
		SoapObject request = new SoapObject(this.namespace, method);//Création de la requête

		if (param!=null && value!=null){// si param et value non null
			request.addProperty(param,value);//On ajout la propriété 
		}
		envelope.setOutputSoapObject(request);//On met notre requete dans l'envelope SOAP
	}

	/** Fonction qui envoie notre message SOAP **/

	public void post() throws IOException, XmlPullParserException{
		String add=this.address+":"+this.port+"/"+this.webservice;
		System.out.println(add);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(add);//Déclaration d'un HTTPTransport
		androidHttpTransport.call("", envelope);//On fait transité notre envelope sur le protocole HTTP

	}

	/** Fonction permettant de récupérer la réponse d'une méthode d'un webservice via SOAP **/

	public SoapPrimitive getResponse(){


		try {
			this.post();// on envoie notre requete avant de récupérer la réponse
		} catch (IOException ex) {
			Logger.getLogger(Soapcommunicator.class.getName()).log(Level.SEVERE, null, ex);
		} catch (XmlPullParserException ex) {
			Logger.getLogger(Soapcommunicator.class.getName()).log(Level.SEVERE, null, ex);
		}
		SoapObject objetSOAP = (SoapObject)envelope.bodyIn;//Récupération du corps de la réponse
		return (SoapPrimitive) objetSOAP.getProperty(0);//On retourne la première propriété
	}
	public SoapObject getRep(){


		try {
			this.post();// on envoie notre requete avant de récupérer la réponse
		} catch (IOException ex) {
			Logger.getLogger(Soapcommunicator.class.getName()).log(Level.SEVERE, null, ex);
		} catch (XmlPullParserException ex) {
			Logger.getLogger(Soapcommunicator.class.getName()).log(Level.SEVERE, null, ex);
		}
		SoapObject objetSOAP = (SoapObject)envelope.bodyIn;//Récupération du corps de la réponse
		return objetSOAP;//On retourne la première propriété
	}
	public void setMethod(String webservice,String method,String param1,String value1,String param2,String value2){
		this.webservice=webservice;
		SoapObject request = new SoapObject(this.namespace, method);//Création de la requête

		if (param1!=null && value1!=null && param2!=null && value2!=null){// si param et value non null
			request.addProperty(param1,value1);//On ajout la propriété 
			request.addProperty(param2,value2);//On ajout la propriété 
		}
		envelope.setOutputSoapObject(request);//On met notre requete dans l'envelope SOAP
	}
	

}

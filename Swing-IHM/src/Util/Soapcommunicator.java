/**
 * Auteur: HOAREAU Jacky 
 * Discipline: Programmation Syst�me embarqu�e
 * Ann�e: ESIR 2 DOM 2011-2012
 * 
 */
package Util;

import java.io.ObjectInputStream.GetField;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Soapcommunicator {
    private String address,namespace,webservice;
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   
	public Soapcommunicator(String address,String namespace,String webservice){
	this.address=address;
	this.namespace=namespace;
	this.webservice=webservice;
	}
	

	public void setMethod(String method,String param,String value){
		SoapObject request = new SoapObject(this.namespace, method);
		request.addProperty(param,value);
		envelope.setOutputSoapObject(request);
	}
	
	public void setMethod(String method,String param1,String value1,String param2,String value2){
		SoapObject request = new SoapObject(this.namespace, method);
		request.addProperty(param1,value1);
		request.addProperty(param2,value2);
		envelope.setOutputSoapObject(request);
	}
	public void setMethod(String method,String param1,String value1,String param2,String value2,String param3,String value3){
		SoapObject request = new SoapObject(this.namespace, method);
		request.addProperty(param1,value1);
		request.addProperty(param2,value2);
		request.addProperty(param3,value3);
		envelope.setOutputSoapObject(request);
	}
	
	
	public void setMethod(String method){
		SoapObject request = new SoapObject(this.namespace, method);
		envelope.setOutputSoapObject(request);
	}
	
	public void post() {

		try{
				
	HttpTransportSE androidHttpTransport = new HttpTransportSE(this.address+"/"+this.webservice);
	androidHttpTransport.call("", envelope);}
		catch (Exception e) {
		// TODO: handle exception
	}
	}
	
	  public String getResponse(){
	        SoapObject objetSOAP = (SoapObject)envelope.bodyIn;//R�cup�ration du corps de la r�ponse
	        return objetSOAP.getProperty(0).toString();//On retourne la premi�re propri�t�
	        }
	  
	  public void changeAdd(String newAdd){
		  address=newAdd;
	  }
	  
	  public String getAdd(){
		  return address;
	  }
}

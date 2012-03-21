package esir2.rb.projet.wsandroid;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import esir2.rb.projet.processor.Processor;

public class WsAndroidActivator implements BundleActivator, Runnable{

	private boolean end=false;
	BundleContext context;
	private Endpoint e;
	private ServiceRegistration sr;	
	private Processor process;
	//private ServiceReference refs;
	private ServiceReference refs[]; 
	private boolean published=false;

	public void start(BundleContext arg0) throws Exception {
		System.out.println("arg0="+arg0);
		context = arg0;	
		
		System.out.println("context="+context);
		end = false;
		//** Enregistrement **/
		sr = context.registerService(WsAndroidActivator.class.getName(), this, null);
		new Thread(this).start();

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		end = true;
		e.stop();
		sr.unregister();
		System.out.println("server is down!");
	}

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

	@Override
	public void run() {
	
		// TODO Auto-generated method stub
		while (!end) {
		
			try {
				//System.out.println("context="+this.context);
				refs = this.context.getAllServiceReferences(Processor.class.getName(),null);
				
				if (refs != null && refs.length != 0) {
					
					for (ServiceReference servRef : refs) {
						process = (Processor) context.getService(servRef);
							//System.out.println(process.isInSimuMode());			
 					if(!published){
 						published=true;
 						String address = getLocaWsAddress(9000, "wsandroid");
 						
 						
 						WsAndroidImpl server = new WsAndroidImpl(process);			
 						e = Endpoint.publish(address, server);
 						System.out.println(address);
 						System.out.println("Server is Ready!");	
 						String reponse=server.sayHello("Paul");
 						System.out.println("#Serveur#:"+reponse);
 					}
					
					}
				}
				Thread.sleep(500);
			} catch (Exception e) {
		
				e.printStackTrace();
			}
		}
	}
	
	
}

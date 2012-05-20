package esir2.rb.projet.wsandroid;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import esir2.rb.projet.bluetoothindicator.BluetoothIndicator;
import esir2.rb.projet.processor.Processor;

public class WsAndroidActivator implements BundleActivator, Runnable{

	private boolean end=false;
	BundleContext context;
	private Endpoint e;
	private ServiceRegistration sr;	
	private Processor process;
	private BluetoothIndicator bluetoothIndicator;
	//private ServiceReference refs;
	private ServiceReference procrefs[]; 
	private ServiceReference bthirefs[]; 
	private boolean published=false;

	public void start(BundleContext c) throws Exception {
		context =c;	
		System.out.println("context="+context);
		end = false;
		
		//** Enregistrement **/
		sr = context.registerService(WsAndroidActivator.class.getName(), this, null);
		new Thread(this).start();

	}

	@Override
	public void stop(BundleContext c) throws Exception {
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
				procrefs = this.context.getAllServiceReferences(Processor.class.getName(),null);
				bthirefs=this.context.getAllServiceReferences(BluetoothIndicator.class.getName(),null);
				
				if (procrefs != null && procrefs.length != 0) {
					
					for (ServiceReference servRef : procrefs) {
						process = (Processor) context.getService(servRef);

					}
				}
				
				
				if (bthirefs != null && bthirefs .length != 0) {
					
					for (ServiceReference servRef : bthirefs ) {
						bluetoothIndicator = (BluetoothIndicator) context.getService(servRef);
						
					
					}
				}
			
				if(!published){
						published=true;
						String address = getLocaWsAddress(9000, "wsandroid");
						
						WsAndroidImpl server = new WsAndroidImpl(process,bluetoothIndicator);			
						e = Endpoint.publish(address, server);
						System.out.println(address);
						System.out.println("Server is Ready!");	
					}
				
				Thread.sleep(500);
			} catch (Exception e) {
		
				e.printStackTrace();
			}
		}
	}
	
	
}

package esir2.rb.projet.bluetoothindicator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import esir2.rb.projet.bluetoothindicator.threads.ReadThread;
import esir2.rb.projet.bluetoothindicator.threads.WriteThread;
import esir2.rb.projet.processor.Processor;

public class BluetoothIndicator implements  BundleActivator , Runnable {


	private static String  connectionURL="btspp://001107190209:1"; //adresse spp du module bluetooth

	private BundleContext context;
	private ServiceRegistration sr;	
	private boolean connectionEstablished=false;
	private ServiceReference procrefs[]; 
	private boolean end=false;
	private Processor proc;
	private String add;
	private StreamConnection streamConnection;
	private ReadThread r;
	private WriteThread w;

	public void start(BundleContext bc) throws Exception {
		context=bc;
		sr = context.registerService(BluetoothIndicator.class.getName(), this, null);
		new Thread(this).start();



	}

	public void stop(BundleContext bc) throws Exception {
		sr.unregister();
		end=true;
		connectionEstablished=false;
		r.killThread();
		w.killThread();
	}

	public void connect() throws IOException{
		streamConnection=(StreamConnection)Connector.open(connectionURL);
		}

	

	public void setAdd(String a,String addgp,int nb) {
		add=a;
		String state=proc.getState(add, addgp, nb);	
		
		
		r=new ReadThread(streamConnection,proc,add);
		w=new WriteThread(streamConnection, proc,add,addgp,nb);
		
		Thread tRead=new Thread(r);
		tRead.start();
		
		Thread tWrite=new Thread(w);
		tWrite.start();
		
		
	}


	public void run() {

		while (!end) {

			try {
				//System.out.println("context="+this.context);
				procrefs = this.context.getAllServiceReferences(Processor.class.getName(),null);

				if (procrefs != null && procrefs.length != 0) {

					for (ServiceReference servRef : procrefs) {
						proc = (Processor) context.getService(servRef);

					}
				}

				if(!connectionEstablished){

					connect();
					connectionEstablished=true;


				}

			}catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

	}
}

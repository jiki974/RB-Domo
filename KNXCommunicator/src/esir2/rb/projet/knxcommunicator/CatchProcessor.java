package esir2.rb.projet.knxcommunicator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import esir2.rb.projet.processor.Processor;

public class CatchProcessor implements Runnable{
	protected BundleContext context;
	protected Processor proc;
	private boolean end;
	public CatchProcessor(BundleContext bc){
		this.context=bc;
	}
	@Override
	public void run() {


		while (!end) {
	
			try {
				ServiceReference sr2=context.getServiceReference(Processor.class.getName());
				proc=(Processor) context.getService(sr2);
				Thread.sleep(800);}
			  catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	public Processor getProcessor(){
		return proc;
	}
	
	public void killThread(){
		end=true;
	}
}

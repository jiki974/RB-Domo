package esir2.rb.projet.threads;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import esir2.rb.projet.communicator.Communicator;
import esir2.rb.projet.util.Util;

public class SearchCommunicators implements Runnable {


	protected  BundleContext context;
	private  boolean end=false;


	protected Communicator simuCom;
	protected Communicator knxCom;
	protected Communicator bacnetCom;


	public SearchCommunicators( BundleContext bc){
		this.context=bc;
	}

	public void run() {
		while (!end) {
			ServiceReference refs[];
			try {
				refs = context.getAllServiceReferences(Communicator.class.getName(),null);
				if (refs != null && refs.length != 0) {
					for (ServiceReference servRef : refs) { // Pour chaque service 

						//on test

						if(servRef.getProperty("type")=="KNX") 
						{
							knxCom= (Communicator) context.getService(servRef);
						}
						else if (servRef.getProperty("type")=="BACNET"){
							bacnetCom = (Communicator) context.getService(servRef);
						}
						else if (servRef.getProperty("type")=="SIMU"){
							simuCom =  (Communicator) context.getService(servRef);
						}
					}
				}
				Thread.sleep(Util.REFRESH_THREAD_SEARCH_COMMUNICATORS);
			}  catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void killThread(){
		end=true;
	}
	//todo : implementer les verification avant de retourner le communicator
	public Communicator getCommunicator(int techno){
		Communicator com = null;
		
		switch (techno) {
		case Util.SIMU:
		com=simuCom;
		break;
		
		
		case Util.KNX:
		com=knxCom;
		break;
		
		case Util.BACNET:
		com=bacnetCom;
		break;
			
	
		default:
		break;
		}
		return  com;
	}

}

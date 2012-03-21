package esir2.rb.projet.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import esir2.rb.projet.communicator.Communicator;
import esir2.rb.projet.threads.SearchCommunicators;
import esir2.rb.projet.threads.Sequence;


public class Processor implements BundleActivator {



	private Thread searchCommunicatorsThread;
	private Thread sequenceThread;
	private SearchCommunicators sc;
	private ServiceRegistration sr;

	private HashMap<String,Communicator> connections=new HashMap<String, Communicator>();
	private HashMap<String,Sequence> sequences=new HashMap<String ,Sequence>();
	//private HashMap<String,Thread> threads=new HashMap<String ,Thread>();


	public void start(BundleContext bc) throws Exception {

		/** Enregistrement **/

		sr = bc.registerService(Processor.class.getName(), this, null);	

		/** Déclaration du processus de recherche de Communicator **/
		
		sc=new SearchCommunicators(bc);
		searchCommunicatorsThread=new Thread(sc);
		searchCommunicatorsThread.setName("SearchCommunicators Thread");
		
        /** Lancement de la Thread de recherche de Communicator **/
		
		searchCommunicatorsThread.start();
	}

	public void stop(BundleContext bc) throws Exception {
		sc.killThread();
		sr.unregister();
	}



	/** Fonction permettant la connexion à une addresse et une technologie voulue
	 * 
	 * @param add - addresse Ip ex :192.168.1.155
	 * @param techno - technologie
	 * 
	 */
	
	public void connect(String add,int techno){
		
		if(connections.containsKey(add)){// Si l'adresse est déja utilisée
			System.out.println(add+" is already in use");	
		}
		
		else{// si aucune connexion a été entamée
			
			try {
				
				/** Récupération du communicator correspondant à la techno voulue **/
				
				Communicator communicator=sc.getCommunicator(techno);
				
				if(communicator.openConnection(add))// Si la l'ouverture de connexion est réussie à l'adresse indiquée
				{
					/** Rajout de la connexion dans Connections **/
					
					connections.put(add,communicator);
					
					System.out.println("nb of connections = "+connections.size());
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}

	}

	
	/** Fonction permettant la déconnexion à une addresse
	 * 
	 * @param add-addresse Ip ex :192.168.1.155
	 * 
	 */
	
	public void disconnect(String add){

		if(connections.containsKey(add)){//Si une connexion est ouverte à cette adresse

			/** Récupération du communicator correspondant **/
			
			Communicator communicator=connections.get(add);
			
			/**  Arrêt de la séquence **/
			stopSequence(add);
			
			/** Fermeture de la connexion **/
			
			communicator.closeConnection();
			
			/** Retrait de la connection dans Connections **/
			
			connections.remove(add);
			
			System.out.println("nb of connections = "+connections.size());

		}
		
		else{ //Si aucune connexion est ouvert à cette adresse
			System.out.println("no connection at "+add);	
		}


	}

	/** Fonction permettant l'execution d'une séquence
	 * 
	 * @param add - addresse Ip ex :192.168.1.155
	 * @param s   - sequence voulue ex : 1-2-3-4
	 * 
	 */
	
	public void startSequence(String add,String s){

		if(connections.containsKey(add) && sequences.containsKey(add)){ //Si une connection a été lancé a cette adresse ainsiqu'une séquence
			System.out.println("one sequence have been already launched at "+add);	
		}

		else if (!connections.containsKey(add)){//Si il n'y a pas de connection ouverte
			System.out.println("no connection at "+add);	
		}


		else{	// Si il y a une connection de lancée à cette adresse mais aucune séquence

			/** Recupération du communicator correspondant **/

			Communicator communicator=connections.get(add);

			/** Création de la sequence **/
			Sequence seq=new Sequence(communicator,s);


			/** Lancement de la sequence **/
			sequences.put(add,seq);
			//sequenceThread =new Thread(seq) ;
			//sequenceThread.start();	
			seq.run();
			/**Ajout de la sequence **/

			
		}	


	}

	public void resumeSequence(String add){
		if(sequences.containsKey(add)){ 
			Sequence seq=sequences.get(add); 
			seq.resume(); 
		}

	}

	public void pauseSequence(String add){
		if(sequences.containsKey(add)){ 
			Sequence seq=sequences.get(add); 
			seq.pause(); 		
		}
	}

	public void stopSequence(String add){
		if(sequences.containsKey(add)){ 
			Sequence seq=sequences.get(add); 
			seq.stop(); 
			sequences.remove(add);
		}
	}

	public void setSequenceSpeed(String add,int speed){
		if(sequences.containsKey(add) ){ 
			Sequence seq=sequences.get(add); 
			seq.setSpeed(speed);
		}
	}	

	public int getSequenceSpeed(String add){
		int speed=0;
		if(sequences.containsKey(add) ){ 
			Sequence seq=sequences.get(add); 
			speed=seq.getSpeed();
		}
		return speed;
	}


	public boolean isConnected(String add){
		return connections.containsKey(add);
	}

	public String getConnections(){
		String u="connections :";
		Set adds= connections.keySet();
		Iterator it=adds.iterator();

		while(it.hasNext()){
			Object actual=it.next();
			u+="["+actual+":"+connections.get(actual).getTechno()+"]";

		}

		return  u;

	}


	public String getSequences(){
		String u="sequences :";
		Set adds= sequences.keySet();
		Iterator it=adds.iterator();

		while(it.hasNext()){
			Object actual=it.next();
			u+="["+actual+":"+sequences.get(actual).getSequence()+"]";

		}

		return  u;
	}


	public boolean verifAddress(String add){
		return false; 

	}

}

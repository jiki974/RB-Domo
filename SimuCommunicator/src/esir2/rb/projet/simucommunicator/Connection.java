package esir2.rb.projet.simucommunicator;

import java.util.HashMap;

public class Connection {


	private Boolean[] lampsON= {false,false,false,false};
	private HashMap<String,SimuListener> listeners=new HashMap<String,SimuListener>();
	private HashMap<String,ButtonSimulation> buttons=new HashMap<String,ButtonSimulation>();
	private boolean connected=false;
	private String add;

	/**
	 * Constructeur d'un connection
	 * @param ipadd indique l'adresse ip
	 */
	public Connection(String ipadd){
		add=ipadd;
		connected=true;
		if(!buttons.containsValue(ipadd)){
			ButtonSimulation bs=new ButtonSimulation("Simu mode : "+ipadd);
			buttons.put(ipadd, bs);
		}
	}


	public boolean isConnected(){
		return connected;
	}

	/**
	 * Fonction permettant l'allumage d'une lampe
	 * @param address_nb indique l'adresse de la lampe
	 */
	public void writeOn(int address_nb)  {
		System.out.println("Lamp "+address_nb+" is on at "+add);
		lampsON[address_nb-1]=true;
	}

	/**
	 * Fonction permettant d'éteindre une lampe
	 * @param address_nb
	 */
	public void writeOff(int address_nb) {
		System.out.println("Lamp "+address_nb+" is off");
		lampsON[address_nb-1]=false;
	}

	/**
	 * Fonction permettant d'éteindre toutes les lampes
	 */
	public void allOff() {
		for(int i=1;i<5;i++) this.writeOff(i);	
	}


	/**
	 * Fonction permettant de lire l'état d'une lampe
	 * @param address_nb indique l'adresse de la lampe
	 * @return l'etat d'une lampe
	 */
	public boolean readState(int address_nb){
		return  lampsON[address_nb]; 
	}


	/**
	 * Fonction permettant d'affecter un listener
	 * @param gpadd indique une adresse de groupe
	 */
	public void setListener(String gpadd) {

		if(!listeners.containsKey(gpadd)){// Si le listener n'existe pas déja

			SimuListener list=new SimuListener(gpadd);// Alors on le crée

			if(gpadd.equals("0/2/0")){

				if(buttons.containsKey(add)){
					buttons.get(add).setPauseListener(list);// Si c'est /0/2/0 on met un pauseListener
				}

			}

			else if(gpadd.equals("0/2/1")){

				if(buttons.containsKey(add)){
					buttons.get(add).setResumeListener(list);// Si c'est /0/2/0 on met un pauseListener
				}//Si c'est 0/2/1 on met un resumeListener

			}

			listeners.put(gpadd,list);//on ajoute le listener dans notre hashmap

		}
	}


	/**
	 * Fonction permettant la suppression de listener
	 * @param gpadd indique une adresse de groupe
	 */
	public void removeListener(String gpadd){

		if(listeners.containsKey(gpadd)){	// Si le listener existe dans la hashmap

			if(gpadd.equals("0/2/0")){
				if(buttons.containsKey(add)){
					buttons.get(add).removePauseListener(listeners.get(gpadd));// Si c'est /0/2/0 on met un pauseListener
				}
			}
			else if(gpadd.equals("0/2/1")){
				if(buttons.containsKey(add)){
					buttons.get(add).removeResumeListener(listeners.get(gpadd));// Si c'est /0/2/1 on supprime un resumeListene
				}
			}
			listeners.remove(gpadd);// on supprime le listener de notre hashmap
		}

	}

	/**
	 * Fonction permettant de connaitre si l'adresse de groupe est une adresse de source
	 * @param gpadd indique une adresse de groupe
	 * @return un boolean indiquant si l'adresse et une adresse source
	 */
	public boolean  isSource(String gpadd){
		boolean res=false;
		if(listeners.containsKey(gpadd)) res=listeners.get(gpadd).isSource();//Si le listener existe on récupère le résultat depuis le buttonsimulator
		return res;
	}

	/**
	 * Fonction permettant de connaitre si l'adresse de groupe est une adresse de destination
	 * @param gpadd indique une adresse de groupe
	 * @return un boolean indiquant si l'adresse et une adresse de destination
	 */
	public boolean isDestination(String gpadd){
		boolean res=false;
		if(listeners.containsKey(gpadd))res=listeners.get(gpadd).isDestination();//Si le listener existe on récupère le résultat depuis le buttonsimulator
		return res;
	}	


	/**
	 * Fonction permettant de mettre à 0 
	 * @param gpadd indique une adresse de groupe
	 */
	public void resetListener(String gpadd){
		listeners.get(gpadd).reset();	
	}

	public String getValue(String gpadd) {
		String ret="";
		String[] temp=gpadd.split("/");
		int last=Integer.valueOf(temp[temp.length-1]);
		if(lampsON[last]) ret="1";
		else ret="0";
		return 	ret;
	}


}

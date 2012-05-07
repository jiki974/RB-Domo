package esir2.rb.projet.simucommunicator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SimuListener implements ActionListener {


	private boolean isdestination=false;
	private boolean issource=false;
	private String group;

	/**
	 * Constructeur d'un SimuListener
	 * @param addgp indique une adresse de groupe
	 */
	public SimuListener(String addgp){
	this.group=addgp;
	}

	/**
	 * Fonction appelée lors d'un clic sur un bouton
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button=(JButton) e.getSource();// On cast la source du clic en JButton
		ButtonSimulation.toggleColor(button);//On change le bouton de couleur
		isdestination=true;//Forçage en tant que destination
	}


	/**
	 * Fonction permettant d'indiquer si l'adresse de groupe actuelle est une destination
	 * @return un boolean indiquant si c'est une destination
	 */
	public boolean isDestination(){
		return isdestination;
	}

	/**
	 * Fonction permettant d'indiquer si l'adresse de groupe actuelle est une source
	 * @return un boolean indiquant si c'est une source
	 */
	public boolean isSource(){
		return issource;
	}

	/**
	 * Fonction permettant de retourner l'adresse de groupe associer au listener
	 * @return une adresse de groupe
	 */
	public String getGroup(){
		return group;
	}

	/**
	 * Fonction permettant de mettre à zero les variable indiqaunt si une adresse de groupe est soit une destination soit une source
	 */
	public void reset(){
		isdestination=false;
		issource=false;
	}

}

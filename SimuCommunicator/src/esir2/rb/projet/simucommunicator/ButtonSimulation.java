package esir2.rb.projet.simucommunicator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class ButtonSimulation extends JFrame {

	private static JButton bPause;
	private static JButton bResume;

	/**
	 * Constructeur de ButtonSimulation
	 * @param title indique le titre de la fenetre
	 */
	public ButtonSimulation(String title){

		//	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icons/main_icon.png")));
		this.setTitle(title);
		this.setPreferredSize(new Dimension(300, 200));


		try {// On essaie de changer le style
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 

		catch (Exception e) {
			System.out.println(e.getMessage());
		}


		this.setLocationRelativeTo(null);
		this.setResizable(false);// On utilise une taille fixe
		this.pack();

		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));//On utilise une vue Grille

		bPause = new JButton("Pause");// Création du bouton pause
		bPause.setFont(new Font("Tahoma", Font.PLAIN, 28));// personalisation du bouton pause

		getContentPane().add(bPause);// Ajout du bouton pause au content panel

		bResume = new JButton("Resume");// Crétion du bouton resume
		bResume.setFont(new Font("Tahoma", Font.PLAIN, 28));// personalisation du bouton resume

		getContentPane().add(bResume);// Ajout du bouton resume  au content panel

		this.setVisible(true);// On rend visible notre fenetre


	}



	/**
	 * Fonction permettant de mettre un listener sur le bouton pause
	 * @param listener indique une Listener de groupe en simulation
	 */
	public void setPauseListener(SimuListener listener){
		bPause.addActionListener(listener);
	}


	/**
	 * Fonction permettant de mettre un listener sur le bouton resume
	 * @param listener indique une Listener de groupe en simulation
	 */
	public void setResumeListener(SimuListener listener){
		bResume.addActionListener(listener);
	}


	/**
	 * Fonction permettant de supprimer un listener sur le bouton pause
	 * @param listener indique une Listener de groupe en simulation
	 */
	public void removePauseListener(SimuListener listener){
		bPause.removeActionListener(listener);
	}

	/**
	 * Fonction permettant de supprimer un lsitener sur le bouton resume
	 * @param listener indique une Listener de groupe en simulation
	 */
	public void removeResumeListener(SimuListener listener){
		bResume.removeActionListener(listener);
	}

	/**
	 * Fonction permettant de permuter de couleur
	 * @param a indique un bouton
	 */
	public static  void toggleColor(JButton a){
		if(a.equals(bPause)){
			bPause.setForeground(Color.RED);	
			bResume.setForeground(Color.BLACK);		
		}
		else{
			bResume.setForeground(Color.RED);	
			bPause.setForeground(Color.BLACK);		
		}

	}


}
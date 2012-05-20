package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class TechnoListener  implements ActionListener  {

	
	private static String techno="0";
	
	public void actionPerformed(ActionEvent ev) {
		JComboBox spinner=(JComboBox) ev.getSource();
		String tech= (String) spinner.getSelectedItem();
		if (tech.equals("SIMU")) techno="0";
		else if (tech.equals("KNX")) techno="1";		
	}

	public static String getTechno(){
		return techno;
	}
}

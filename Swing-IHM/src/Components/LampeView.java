package Components;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import Util.Util;

@SuppressWarnings("serial")
public class LampeView extends  JCheckBox {
	
	private static ImageIcon iconLampOn = Util.createImageIcon("/images/bulb_yellow.png","led on");
	private static ImageIcon iconLampOff = Util.createImageIcon("/images/bulb.png", "led off");
	private int id=0;
	public LampeView(int i, ActionListener l){
		super(iconLampOff);
		super.addActionListener(l);
		id=i;
	}
	
	public void turnOn(){
    setIcon(iconLampOn);
	}

	public void turnOff(){
	setIcon(iconLampOff);
	}
	
	public void setId(int i){
		id=i;
	}
	
	public int getId(){
		return id;
	}
	

}
	
	



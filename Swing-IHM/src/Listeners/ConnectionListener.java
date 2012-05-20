package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Control.Connection;
import View.IHM;

public class ConnectionListener implements ActionListener {

	private String addWs="";

	private String addTechno="";
	private IHM myIHM;
	private Connection con;

	public ConnectionListener(IHM i){
		myIHM=i;
		con=new Connection(myIHM);
	}

	public void actionPerformed(ActionEvent ev) {
		
		JButton b=(JButton) ev.getSource();


		if(b.getText().equals("Connect")){

			if(verifField("Techno Add ",myIHM.getTechnoAdd())){
				addTechno=myIHM.getTechnoAdd();
			}

			if(verifField("WebService Add ",myIHM.getWsAdd())){
				addWs=myIHM.getWsAdd();

				String techno=TechnoListener.getTechno();

				con.open(addTechno, techno);
			}
		}
		
		else if (b.getText().equals("Disconnect")){
			con.close(addTechno);
		}


	}


	public boolean verifField(String source,String ip){

		boolean ok=false;
		String[] temp;

		if (ip.equals("")){
			JOptionPane.showMessageDialog(null, source+" is empty !", "Warning empty field !",2 );
		}
		else if (!ip.contains(".")){
			JOptionPane.showMessageDialog(null, source+" is malformed !", "Warning malformed field !",2 );
		}

		else {
			temp=ip.split("\\.");
			if (temp.length!=4) JOptionPane.showMessageDialog(null, source+" bad length !", "Warning malformed field !",2 );
			if(verifArray(temp)) ok=true;
			else JOptionPane.showMessageDialog(null, source+" is malformed !", "Warning malformed field !",2 );

		}

		return ok;	
	}


	public Boolean verifArray(String[] add){
		boolean ok=false;
		int nb=4;
		for(int i=0;i<add.length;i++){
			int a=0;

			try {
				a=Integer.valueOf(add[i]);
			} catch (Exception e) {
				System.out.println("only numbers are expected !");
			}

			if(i>=1 && i<3 ){
				if(a>=0 && a<254){

					nb--;
				}
			}
			else{

				if(a>0 && a<254){
					nb--;
				}	
			}


		}

		if(nb==0) ok=true;
		return ok;	
	}


}

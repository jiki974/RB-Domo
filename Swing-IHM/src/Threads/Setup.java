package Threads;

import Util.Soapcommunicator;
import Util.Util;
import View.IHM;


public class Setup implements Runnable{
	

	private Boolean end =false;
	private String add="";
	private String techno="";

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address = Util.getLocalAdd();	
	private IHM myIHM;
	
	public Setup(IHM i){
		myIHM=i;
	}
	
	private Boolean[] lampsON= {false,false,false,false};

	public void run() {





		while(!end){	

			add=myIHM.getTechnoAdd();
			techno=myIHM.getChosenTechno();


			while(Util.isConnected(add)&&techno.equals("SIMU")){
				/*
				if(getSetState(1)==false)allumeOnly1();
				if(getSetState(2)==false)allumeOnly2();
				if(getSetState(3)==false)allumeOnly3();
				if(getSetState(4)==false)allumeOnly4();

				 */
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				if(getSetState(1)==true)allumeOnly1();
				else{eteindsOnly1();}
				if(getSetState(2)==true)allumeOnly2();
				else{eteindsOnly2();}
				if(getSetState(3)==true)allumeOnly3();
				else{eteindsOnly3();}
				if(getSetState(4)==true)allumeOnly4();
				else{eteindsOnly4();}
				 */


				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}




/*

			while(Util.isConnectedToTech(add,"KNX")&&techno.equals("KNX")){

				if(getSetState(1)==false){
					//allumeOnly1();

				}
				if(getSetState(2)==false){
					//allumeOnly2();
					ledOn(2);
				}
				if(getSetState(3)==false){
					//allumeOnly3();
					ledOn(3);
				}
				if(getSetState(4)==false){
					//allumeOnly4();
					ledOn(4);
				}

				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				if(getSetState(1)==true){
					//allumeOnly1();
					ledOn(1);
				}
				//	else{eteindsOnly1();ledOff(1);}
				if(getSetState(2)==true){
					//allumeOnly2();
					ledOn(2);
				}
				//	else{eteindsOnly2();ledOff(2);}
				if(getSetState(3)==true){
					//allumeOnly3();
					ledOn(3);
				}
				//	else{eteindsOnly3();ledOff(3);}
				if(getSetState(4)==true){
					//allumeOnly4();
					ledOn(4);
				}
				else{//eteindsOnly4();ledOff(4);
					ledOff(4);
				}




				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}*/
		}
	}


	public  boolean getSetState(int nButtonLamp){
		 
		
		
		/*
		boolean state=false;		

		switch (nButtonLamp){
		case(1):if(state1==true)state=true;break;
		case(2):if(state2==true)state=true;break;
		case(3):if(state3==true)state=true;break;
		case(4):if(state4==true)state=true;break;
		default:break;
		}
		return state;*/
		
		return lampsON[nButtonLamp];

	}

	public void ledOn(int nbLamp){
	String numLamp=String.valueOf(nbLamp);
	String addCible=myIHM.getTechnoAdd();
	sm.setMethod("writeOn","add",addCible,"lampe",numLamp);

	try {

		sm.post();

	} catch (Exception e){
		System.out.println(e.getMessage());
	}

	}


	public void ledOff(int nbLamp){


		String numLamp=String.valueOf(nbLamp);
		String addCible=myIHM.getChosenTechno();
		sm.setMethod("writeOff","add",addCible,"lampe",numLamp);

		try {

			sm.post();

		} catch (Exception e){
			System.out.println(e.getMessage());
		}

/*
 *
		switch (nbLamp){
		case(1):numLamp="1";break;
		case(2):numLamp="2";break;
		case(3):numLamp="3";break;
		case(4):numLamp="4";break;
		default: break;
		}*/
		
		
		
		
	}
	
	
	
	public  void setState(int nButtonLamp, boolean state){
		
		lampsON[nButtonLamp]=state;
		/*
		switch (nButtonLamp){
		case(1):state1=state;break;
		case(2):state2=state;break;
		case(3):state3=state;break;
		case(4):state4=state;break;
		default:break;
		}*/
	}

}
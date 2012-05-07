import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.WebParam;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * chen.java
 *
 * Created on 6 févr. 2012, 23:42:04
 */
/**
 * 
 * @author taekeso-kensei
 */
public class chen extends javax.swing.JFrame {

	private Thread chenillardThread;
	private Thread technoThread,retourEtatThread,setupThread,retourKnxThread;
	private Thread killStopThread,killPauseThread,killResumeThread;

	private Soapcommunicator sm;	
	private String webservice="wsandroid";
	private String namespace="http://wsandroid.projet.rb.esir2/";
	private String address=Util.getLocalAdd();	

	

	/** Creates new form chen */
	public chen() throws IOException {
		// boolean end=false;
		initComponents();

		bStop.addActionListener(new StopSequence(this));
		bStart.addActionListener(new StartSequence(this));
		bSetup.addActionListener(new GetSequence(this));
		bConnect.addActionListener(new Connect(this));
		bDisconnect.addActionListener(new Disconnect(this));

		bStart.setIcon(iconButtonAdd);
		bPauseResume.setIcon(iconButtonStart);
		bStop.setIcon(iconButtonStop);
		bSetup.setIcon(iconButtonSetup);
		bPauseResume.addActionListener(new PauseResume(this));

		jSlider1.addChangeListener(new SliderListener(this));

		lampe1.addActionListener(new Number1());
		lampe2.addActionListener(new Number2());
		lampe3.addActionListener(new Number3());
		lampe4.addActionListener(new Number4());

		//*retourKnxThread= new Thread(new retourKnx());
		//retourKnxThread.start();
		
		
		chenillardThread= new Thread(new chenillard());
		//technoThread= new Thread(new technoThread());
		//technoThread.start();
		//retourEtatThread= new Thread(new retourEtat());
		//retourEtatThread.start();
		killStopThread=new Thread(new killStop());
		killPauseThread=new Thread(new killPause());
		killResumeThread=new Thread(new killResume());
		

	}

	public void allume1() {
		lampe1.setIcon(iconLedOn);
		lampe2.setIcon(iconLedOff);
		lampe3.setIcon(iconLedOff);
		lampe4.setIcon(iconLedOff);
	}

	public void allume2() {
		lampe1.setIcon(iconLedOff);
		lampe2.setIcon(iconLedOn);
		lampe3.setIcon(iconLedOff);
		lampe4.setIcon(iconLedOff);
	}

	public void allume3() {
		lampe1.setIcon(iconLedOff);
		lampe2.setIcon(iconLedOff);
		lampe3.setIcon(iconLedOn);
		lampe4.setIcon(iconLedOff);
	}

	public void allume4() {
		lampe1.setIcon(iconLedOff);
		lampe2.setIcon(iconLedOff);
		lampe3.setIcon(iconLedOff);
		lampe4.setIcon(iconLedOn);
	}


	public static void allumeOnly1() {
		lampe1.setIcon(iconLedOn);
	}

	public static void allumeOnly2() {
		lampe2.setIcon(iconLedOn);
	}

	public static void allumeOnly3() {
		lampe3.setIcon(iconLedOn);
	}

	public static void allumeOnly4() {
		lampe4.setIcon(iconLedOn);
	}

	public static void eteindsOnly1() {
		lampe1.setIcon(iconLedOff);
	}
	public static void eteindsOnly2() {
		lampe2.setIcon(iconLedOff);
	}
	public static void eteindsOnly3() {
		lampe3.setIcon(iconLedOff);
	}
	public static void eteindsOnly4() {
		lampe4.setIcon(iconLedOff);
	}



	public static void offAll() {
		lampe1.setIcon(iconLedOff);
		lampe2.setIcon(iconLedOff);
		lampe3.setIcon(iconLedOff);
		lampe4.setIcon(iconLedOff);

	}
	//thread qui fait un unique stop séquence
	public class killStop implements Runnable{
		private Soapcommunicator sm;	
		private String webservice="wsandroid";
		private String namespace="http://wsandroid.projet.rb.esir2/";
		private String address="http://192.168.1.106";
		@Override
		public void run() {

			System.out.println("hello from killThread");

			address=Util.getLocalAdd();	
			String addCible=chen.getChosenAdd();
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"stopSequence","add",addCible);

			try {

				sm.post();
				setIsKilled(true);
				KillKillerThread();				
				System.out.println("killed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	public class killResume implements Runnable{
		private Soapcommunicator sm;	
		private String webservice="wsandroid";
		private String namespace="http://wsandroid.projet.rb.esir2/";
		private String address="http://192.168.1.106";
		@Override
		public void run() {

			System.out.println("hello from killThread");

			address=Util.getLocalAdd();	
			String addCible=chen.getChosenAdd();
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"resumeSequence","add",addCible);

			try {

				sm.post();
				setIsKilled(true);
				KillKillerThread();				
				System.out.println("killed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	public class killPause implements Runnable{

		@Override
		public void run() {

			System.out.println("hello from killpauseThread");

			address=Util.getLocalAdd();	
			String addCible=chen.getChosenAdd();
			sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
			sm.setMethod(webservice,"pauseSequence","add",addCible);

			try {

				sm.post();
				setIsKilled(true);
				KillKillerThread();				
				System.out.println("killed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	//thread qui affiche les retours d'états venant de knx
	public class retourKnx implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String add=chen.getChosenAdd();	
			System.out.println("hello from retour knx");


			while (true){

				boolean ListenerLamp1=readState(add,"0/3/0");
				boolean ListenerLamp2=readState(add,"0/3/1");
				boolean ListenerLamp3=readState(add,"0/3/2");
				boolean ListenerLamp4=readState(add,"0/3/3");

				offAll();

				if(ListenerLamp1)allumeOnly1();
				if(ListenerLamp2)allumeOnly2();
				if(ListenerLamp3)allumeOnly3();
				if(ListenerLamp4)allumeOnly4();
				System.out.println("youhouuuu");
				try {
					Thread.sleep(500); 
					System.out.println("youhou");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}

	}
	//thread qui gère le basculement entre affichage simu ou knx
	public class technoThread implements Runnable {

		public void run() {
			String add=chen.getChosenAdd();
			while(true){
				techno=getChosenTechno();
				add=chen.getChosenAdd();
				System.out.println("hello from technoThread");
				if(isConnectedToTech(add, techno)&&techno.equals("SIMU"))
				{
					stopRetourKnxThread();//j'arrete la thread d'affichage des retours
					
				}
				else{
					if(isConnectedToTech(add, techno)&&techno.equals("KNX")){						
						stopThread();//j'arrète l'affichage du chenillard mode simu
						startRetourKnxThread();
					}
				}


				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}
	//thread qui gère l'affichage de la connection sur le swing
	public class retourEtat implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			String currentTech=chen.getChosenTechno();
			boolean end=false;
			while (true){

				while (!end){

					String add=chen.getChosenAdd();
					String tech=chen.getChosenTechno();
					System.out.println("hello from retour etat");
					boolean state=isConnected(add);
					if (state){

						if(getChosenTechno().equals("KNX")&&isConnectedToTech(add,"KNX")){
							currentTech="KNX";
							//stopThread();	
							jLabel1.setText("Connecté à "+add +" mode KNX" );

						}
						 if(getChosenTechno().equals("SIMU")&&isConnectedToTech(add,"SIMU")){
							currentTech="SIMU";	
							//cheni.startThreadChenillard()
							//stopRetourKnxThread();
							jLabel1.setText("Connecté à "+add +" mode SIMU" );
						
						}

						 	if (!getChosenTechno().equals(currentTech)){
							jLabel1.setText("Connecté à "+add +" techno invalide" );
							setInvalidTechno(false);//la techno est invalide
							end=true;//empèche la thread de faire les tests tant que la techno réelle et la techno sélectionné n'est pas différente
							
							
						}
						 	
						 	
						else{jLabel1.setText("Connecté à "+add +" mode " + currentTech);
						setInvalidTechno(true);//la techno est valide
						
						}
						 	
						 	
						 	

					}

					else{jLabel1.setText("Déconnecté" );}


					try {
						Thread.sleep(500);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				end=!getChosenTechno().equals(currentTech);
			}
		}

	}
	//Thread qui gère la séquence d'installation sur le swing
	public class setupThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){	
				String add=getChosenAdd();
				while(isConnected(add)&&techno.equals("SIMU")){

					if(getSetState(1)==false)allumeOnly1();
					if(getSetState(2)==false)allumeOnly2();
					if(getSetState(3)==false)allumeOnly3();
					if(getSetState(4)==false)allumeOnly4();


					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


					if(getSetState(1)==true)allumeOnly1();
					else{eteindsOnly1();}
					if(getSetState(2)==true)allumeOnly2();
					else{eteindsOnly2();}
					if(getSetState(3)==true)allumeOnly3();
					else{eteindsOnly3();}
					if(getSetState(4)==true)allumeOnly4();
					else{eteindsOnly4();}



					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}



				}

				while(isConnectedToTech(add,"KNX")&&techno.equals("KNX")){

					if(getSetState(1)==false){
						//allumeOnly1();
						ledOn(1);
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
					else{eteindsOnly1();ledOff(1);}
					if(getSetState(2)==true){
						//allumeOnly2();
						ledOn(2);
					}
					else{eteindsOnly2();ledOff(2);}
					if(getSetState(3)==true){
						//allumeOnly3();
						ledOn(3);
					}
					else{eteindsOnly3();ledOff(3);}
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



				}
			}
		}
	}
	public class chenillard implements Runnable {

		public int i = 1;
		private Soapcommunicator sm;
		private String webservice = "wsandroid";
		private String namespace = "http://wsandroid.projet.rb.esir2/";
		private String address = "";
		private Boolean[] state = { null, null, null, null };
		public String response = "";

		@Override
		public void run() {

			System.out.println("Hello from a thread!");
			address = Util.getLocalAdd();
			System.out.println("from listener: " + address);
			sm = new Soapcommunicator(address, 9000, namespace);

			while (true) {

				
					for(i=0;(i<4);i++)

						try{

							//if (end)chen.offAll();

							makeSequence(Sequence);
							System.out.println("seq=" + seq+ "i= "+i);
							int nb=seq.get(i);
							System.out.println(nb);
							switch (nb){

							case(1):offAll();allumeOnly1(); 
							break;
							case(2):offAll();allumeOnly2(); 
							break;
							case(3):offAll();allumeOnly3(); 
							break;
							case(4):offAll();allumeOnly4(); 
							break;
							}


							Thread.sleep(SpeedChosen);
						} catch (InterruptedException e) {							
							e.printStackTrace();
						}



				}

			}
		

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		lampe1 = new javax.swing.JCheckBox(iconLedOff);
		lampe2 = new javax.swing.JCheckBox(iconLedOff);
		lampe3 = new javax.swing.JCheckBox(iconLedOff);
		lampe4 = new javax.swing.JCheckBox(iconLedOff);

		bStop = new javax.swing.JButton();
		bStart = new javax.swing.JButton();
		bSetup = new javax.swing.JButton();
		bDisconnect = new javax.swing.JButton();
		bPauseResume = new javax.swing.JButton();
		bConnect = new javax.swing.JButton();

		champIp = new javax.swing.JTextField();

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();

		voletDeroulant = new javax.swing.JComboBox();

		jSlider1 = new javax.swing.JSlider();

		jSeparator1 = new javax.swing.JSeparator();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		voletDeroulant.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"SIMU", "KNX" }));
		voletDeroulant.addActionListener(new java.awt.event.ActionListener() {
			
			/** Action à executer en fonction de la techno choisie **/
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				//exemple
				String add=chen.getChosenAdd();// 192.168.1.5
				techno=getChosenTechno();// Simu
				
			
					if(techno.equals("SIMU")  && isConnectedToTech(add,"KNX")) stopRetourKnxThread();//j'arrete la thread d'affichage des retours
						
				
					else if (techno.equals("KNX") && isConnectedToTech(add,"SIMU") ) {					
							stopThread();//j'arrète l'affichage du chenillard mode simu
							startRetourKnxThread();
						}
					}


				
					
			}
			

		);

		jSlider1.setMaximum(1500);
		jSlider1.setMinimum(500);
		jSlider1.setValue(800);
		jSlider1.setInverted(true);

		Hashtable labelTable = new Hashtable();

		labelTable.put(new Integer(1500), new JLabel("Slow"));
		labelTable.put(new Integer(500), new JLabel("Fast"));
		jSlider1.setLabelTable(labelTable);
		jSlider1.setPaintLabels(true);

		jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSlider1StateChanged(evt);
			}
		});

		champIp.setText("Entrer une ip");
		champIp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				champIpActionPerformed(evt);
			}

		});

		lampe1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lampe1ActionPerformed(evt);
			}
		});

		lampe3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lampe3ActionPerformed(evt);
			}
		});

		lampe4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lampe4ActionPerformed(evt);
			}
		});

		lampe2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lampe2ActionPerformed(evt);
			}
		});

		//bStop.setText("Stop");
		bStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bStopActionPerformed(evt);
			}
		});

		//bStart.setText("Start");
		bStart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bStartActionPerformed(evt);
			}
		});

		//bSetup.setText("Connect");
		bSetup.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bSetupActionPerformed(evt);
			}
		});

		bDisconnect.setText("Disconnect");
		bDisconnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bDisconnectActionPerformed(evt);
			}
		});
		//bPauseResume.setText("Pause/Resume");
		bPauseResume.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bPauseResumeActionPerformed(evt);
			}
		});

		bConnect.setText("Connect");
		bConnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bConnectActionPerformed(evt);
			}
		});

		setSize(500, 100);
		setResizable(false);
		

		jLabel2.setText("COMMANDES");

		jLabel3.setText("VISUALISATION");

		jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

		//bSetup.setText("Setup");

		 javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(29, 29, 29)
	                        .addComponent(lampe1)
	                        .addGap(18, 18, 18)
	                        .addComponent(lampe2)
	                        .addGap(18, 18, 18)
	                        .addComponent(lampe3)
	                        .addGap(18, 18, 18)
	                        .addComponent(lampe4))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(59, 59, 59)
	                        .addComponent(jLabel3)))
	                .addGap(27, 27, 27)
	                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(6, 6, 6)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                                    .addComponent(champIp)
	                                    .addComponent(bConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                .addGap(6, 6, 6)
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                                    .addComponent(voletDeroulant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                    .addComponent(bDisconnect)))
	                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                                .addGroup(layout.createSequentialGroup()
	                                    .addComponent(bStart, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                    .addComponent(bPauseResume, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                    .addComponent(bStop, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                    .addComponent(bSetup, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
	                               .addGap(21,21,21)
	                                    .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                   .addComponent(jLabel1))))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(85, 85, 85)
	                        .addComponent(jLabel2)))
	                .addContainerGap(21, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel3)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(jLabel2)
	                        .addGap(18, 18, 18)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(champIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(voletDeroulant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addGap(18,18,18)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(bDisconnect)
	                            .addComponent(bConnect))
	                        .addGap(22, 22, 22)
	                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(bStart)
	                            .addComponent(bStop)
	                            .addComponent(bSetup)
	                            .addComponent(bPauseResume))))
	                .addGap(18, 18, 18)
	                .addComponent(jLabel1)
	                .addGap(81, 81, 81))
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                        .addGap(80,80,80)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                            .addComponent(lampe1)
	                            .addComponent(lampe2)
	                            .addComponent(lampe3)
	                            .addComponent(lampe4)))
	                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                        .addGap(21, 21, 21)
	                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                .addContainerGap(21, Short.MAX_VALUE))
	        );

	        pack();
	}// </editor-fold>



	private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {
		// TODO add your handling code here:
	}

	private void champIpActionPerformed(ActionEvent evt) {
		String add = champIp.getText();
		addressChosen = add;


	}

	private void lampe1ActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur lampe 1");
	}

	private void lampe3ActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur lampe 3");
	}

	private void lampe4ActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur lampe 4");
	}

	private void lampe2ActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur lampe 2");
	}

	private void bStopActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur stop");
	}

	private void bStartActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur start");
	}

	private void bSetupActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur connect");
	}

	private void bDisconnectActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("appui sur disconnect");


	}

	private void bPauseResumeActionPerformed(java.awt.event.ActionEvent evt) {
		if (isPaused()) {
			System.out.println("Sequence sur pause");
		} else {
			System.out.println("Sequence en cours");
		}
	}

	private void bConnectActionPerformed(ActionEvent evt) {
		String add = champIp.getText();


	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = chen.class.getResource(path);
		System.out.println(imgURL);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(chen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(chen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(chen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(chen.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					new chen().setVisible(true);

				} catch (IOException ex) {
					Logger.getLogger(chen.class.getName()).log(Level.SEVERE,
							null, ex);
				}

			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JButton bStop;
	private javax.swing.JButton bStart;
	private javax.swing.JButton bSetup;
	private javax.swing.JButton bDisconnect;
	private javax.swing.JButton bPauseResume;
	private javax.swing.JButton bConnect;

	private static javax.swing.JCheckBox lampe1;
	private static javax.swing.JCheckBox lampe2;
	private static javax.swing.JCheckBox lampe3;
	private static javax.swing.JCheckBox lampe4;

	private static javax.swing.JTextField champIp;

	private static javax.swing.JComboBox voletDeroulant;

	private static javax.swing.JSlider jSlider1;

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;

	private javax.swing.JSeparator jSeparator1;

	private static ImageIcon iconLedOn = createImageIcon("images/bulb_yellow.png",
			"a pretty but meaningless splat");
	private static ImageIcon iconLedOff = createImageIcon(
			"images/bulb.png", "a pretty but meaningless splat");
	private static ImageIcon iconButtonAdd = createImageIcon("images/1336313687_Add.png",
			"a pretty but meaningless splat");
	private static ImageIcon iconButtonStart = createImageIcon("images/1336313734_Play.png",
			"a pretty but meaningless splat");
	private static ImageIcon iconButtonPause = createImageIcon("images/1336313782_Pause.png",
			"a pretty but meaningless splat");
	private static ImageIcon iconButtonStop = createImageIcon("images/1336313761_Stop.png",
			"a pretty but meaningless splat");
	private static ImageIcon iconButtonSetup = createImageIcon("images/1336314879_RecordPressed.png",
			"a pretty but meaningless splat");

	private static boolean end = true;
	public static String addressChosen = "";
	public static String techno = "";
	public static int SpeedChosen = 800;
	public static boolean isPaused = false;
	public static String num = null;
	public static String Sequence = "1-2-3-4";
	private static LampList<Integer>seq;
	public static int LampProgram = 0;
	public static boolean stateSeq=false;
	public static boolean seqIsBeingSet=false;
	public static boolean state1=false;
	public static boolean state2=false;
	public static boolean state3=false;
	public static boolean state4=false;
	public static boolean techIsInvalid=true;
	public static boolean isKilled=false;
	



	public static String getChosenAdd() {
		String add = champIp.getText();
		addressChosen = add;
		return addressChosen;
	}

	public static String getChosenTechno() {
		String technologie = (String) voletDeroulant.getSelectedItem();
		return technologie;
	}
	public static String getTechno(){
		return techno;
	}

	public static int getChosenSpeed() {
		return SpeedChosen;
	}

	public static void setChosenSpeed(int Speed) {
		SpeedChosen = Speed;
	}

	public static boolean isPaused() {
		return isPaused;
	}

	public static String getNum() {

		return num;

	}

	public static void setNum(String nombre) {

		num = nombre;

	}

	public static String getSequence() {

		return Sequence;

	}

	public static void setSequence(String nb) {
		Sequence = Sequence + nb + "-";
	}

	public static void setSeq(String nb) {
		Sequence = nb;
	}

	public static int getNbLampSet() {
		return LampProgram;
	}

	public static void setNbLampSet() {
		LampProgram = LampProgram + 1;
	}

	public static void resetNbLampSet() {
		LampProgram = 0;
	}

	public static void setPaused(boolean etat) {

		if (etat == true) {
			isPaused = true;
		} else {
			isPaused = false;
		}
	}
	public static void setEnded() {

		end=true;
	}

	public static void resetEnded() {

		end=false;
	}
	public static boolean getEnd() {
		return end;
	}

	public static void setLaunchedState(boolean state){
		stateSeq=state; 
	}
	public static boolean getChenState(){
		return stateSeq;
	}

	public static void setSeqIsSet(boolean state){
		seqIsBeingSet=state; 
	}
	public static boolean getSeqSetState(){
		return seqIsBeingSet;
	}


	// End of variables declaration



	private void makeSequence(String s){

		seq=new LampList<Integer>();
		String[] sp=s.split("-");		
		for(int i=0;i<sp.length;i++) seq.add(Integer.valueOf(sp[i]));
	}

	public void pauseThread() {		
		chenillardThread.suspend();
		bPauseResume.setIcon(iconButtonStart);
	}

	public void resumeThread(){		
		chenillardThread.resume();	
		bPauseResume.setIcon(iconButtonPause);
	}

	public void startThreadSetup(){
		setupThread=new Thread(new setupThread());
		setupThread.start();
	}
	public void stopThreadSetup(){		
		setupThread.stop();
		offAll();
	}
	public void startRetourKnxThread(){
		retourKnxThread=new Thread(new retourKnx());
		retourKnxThread.start();
	}
	public void stopRetourKnxThread(){		
		retourKnxThread.stop();		
	}



	public void stopThread(){
		bPauseResume.setIcon(iconButtonStart);
		setLaunchedState(false);
		chen.setEnded();
		chen.offAll();		
		chen.setSeq("1-2-3-4");
		chenillardThread.stop();
		


	}
	public void startThreadChenillard(){
		if(getChenState()==false){
			chen.resetEnded();
			bPauseResume.setIcon(iconButtonPause);
			if (chen.isPaused()==true)setPaused(false);	
			chenillardThread=new Thread(new chenillard());
			chenillardThread.start();
			setLaunchedState(true);
		}
	}
	public void startThreadVisuSwing(){
		chenillardThread=new Thread(new chenillard());
		chenillardThread.start();
	}



	public static boolean getSetState(int nButtonLamp){
		boolean state=false;		

		switch (nButtonLamp){
		case(1):if(state1==true)state=true;break;
		case(2):if(state2==true)state=true;break;
		case(3):if(state3==true)state=true;break;
		case(4):if(state4==true)state=true;break;
		default:break;
		}
		return state;

	}
	public static void setState(int nButtonLamp, boolean state){
		switch (nButtonLamp){
		case(1):state1=state;break;
		case(2):state2=state;break;
		case(3):state3=state;break;
		case(4):state4=state;break;
		default:break;
		}
	}

	public boolean isConnected(String add){
		boolean response = false;
		
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"isConnected","add",add);
		
		String rep=sm.getResponse().toString();

		if (rep.equals("true")) response=true;

		try {			
			sm.post();


		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		return response;
	}
	
	public boolean readState(String add,String addgp){

		boolean response = false;
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"getState","add",add,"addgp",addgp);
		
		String rep=sm.getResponse().toString();

		if (rep.equals("1"))response=true;

		try {	sm.post();

		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		return response;

	}

	public void ledOn(int nbLamp){

		String numLamp="";

		switch (nbLamp){
		case(1):numLamp="1";break;
		case(2):numLamp="2";break;
		case(3):numLamp="3";break;
		case(4):numLamp="4";break;
		default: break;
		}

		
		String addCible=chen.getChosenAdd();
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"writeOn","add",addCible,"lampe",numLamp);

		try {

			sm.post();

		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}	

	public void ledOff(int nbLamp){

		
		String numLamp="";

		switch (nbLamp){
		case(1):numLamp="1";break;
		case(2):numLamp="2";break;
		case(3):numLamp="3";break;
		case(4):numLamp="4";break;
		default: break;
		}

		String addCible=chen.getChosenAdd();
		System.out.println("add cible :"+addCible);
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"writeOff","add",addCible,"lampe",numLamp);

		try {

			sm.post();
			System.out.println("post envoyé");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}	

	// fonction compare si la technologie sélectionnée dans le volet déroulant correspond avec celle qui est connecté		

	public boolean isConnectedToTech(String add,String tech){

		Boolean isConected=isConnected(add);		
		String techno=getChosenTechno();
		if (techno.equals(tech)&&isConected)return true;
		else{return false;}

	}

	public void setInvalidTechno(boolean state){

		if (state==false&&isKilled==false){
			Thread killThread=new Thread(new killStop());
			killThread.start();
		}
		else {}
		techIsInvalid=state;

	}

	public void KillKillerThread(){
		if (isKilled){
			killStopThread.stop();
			setIsKilled(false);
		}		
	}
	public void setIsKilled(boolean state){
		isKilled=state;
	}
	public static boolean getInvalidTechnoState(){
		return techIsInvalid;
	}

	public void pauseSequence(){
		Soapcommunicator sm;	
		String webservice="wsandroid";
		String namespace="http://wsandroid.projet.rb.esir2/";
		String address="";

		chen.setPaused(true);
		address=Util.getLocalAdd();	
		String addCible=chen.getChosenAdd();
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"pauseSequence","add",addCible);

		try {

			sm.post();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void resumeSequence(){

		chen.setPaused(false);
		String addCible=chen.getChosenAdd();
		sm=new Soapcommunicator(address,9000,namespace); // Création de notre Message
		sm.setMethod(webservice,"resumeSequence","add",addCible);

		try {

			sm.post();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}

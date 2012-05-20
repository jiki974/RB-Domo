package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Components.LampeView;
import Control.Sequence;
import Listeners.BthListener;
import Listeners.ConnectionListener;
import Listeners.LampesListener;
import Listeners.SetupListener;
import Listeners.SpeedListener;
import Listeners.TechnoListener;
import Util.Util;

public class IHM extends JFrame {

	private  LampeView lampe1;
	private  LampeView lampe2;
	private  LampeView lampe3;
	private  LampeView lampe4;
	
	private  ArrayList<LampeView> lampes=new ArrayList<LampeView>();
	private JSeparator separator;
	
	private  JTextField ipOfWs;
	private  JTextField ipOfTechno;
	
	
	private JLabel lblAddressOftechno;
	private JLabel chooseModeLabel;
	private JButton bConnect;
	private JButton bDisconnect;
	private JLabel lblSlow;
	private JLabel lblFast;
	private JSeparator seqSeparator;
	private JButton bStop;
	private JButton bStart;
	private JButton bSetup;
	private  JToggleButton bPauseResume;
	private JLabel sequenceControlLabel;
	private JLabel addwslabel;
	private JLabel controllabel;
	private JSlider speedSlider;
	private JLabel lblVisualization;
	private JLabel speedlabel;
	private JComboBox techSpinner;
	private JSeparator speedSeparator;
	private JLabel connectionlabel ;
	private JCheckBox useBth;


	private ImageIcon iconButtonAdd = Util.createImageIcon("/images/Add.png","add");
	private static   ImageIcon iconButtonStart = Util.createImageIcon("/images/Play.png","start");
	private static   ImageIcon iconButtonPause = Util.createImageIcon("/images/Pause.png","pause");
	private  ImageIcon iconButtonStop = Util.createImageIcon("/images/Stop.png","stop");
	private   ImageIcon iconButtonSetup = Util.createImageIcon("/images/Setup.png","setup");


	public IHM(String title){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setTitle(title);
		this.setPreferredSize(new Dimension(900, 500));

		/** Affectation des listeners sur les lampes **/



		try {// On essaie de changer le style
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 

		catch (Exception e) {
			System.out.println(e.getMessage());
		}


		this.setLocationRelativeTo(null);
		this.setResizable(false);// On utilise une taille fixe
		this.pack();

		getContentPane().setLayout(null);

		createComponents();
		addComponents();
		addListeners();

		this.setVisible(true);// On rend visible notre fenetre


	}


	public void createComponents(){
		LampesListener lListener=new LampesListener(this);

		lampe1=new LampeView(1,lListener);
		lampe1.setBounds(6, 176, 137, 137);
		lampe1.setHorizontalAlignment(SwingConstants.CENTER);

		lampe2=new LampeView(2,lListener);
		lampe2.setBounds(129, 176, 137, 137);
		lampe2.setHorizontalAlignment(SwingConstants.CENTER);


		lampe3=new LampeView(3,lListener);
		lampe3.setBounds(268, 176, 137, 137);
		lampe3.setHorizontalAlignment(SwingConstants.CENTER);

		lampe4=new LampeView(4,lListener);
		lampe4.setBounds(411, 176, 137, 137);
		lampe4.setHorizontalAlignment(SwingConstants.CENTER);
		lampes.add(lampe1);
		lampes.add(lampe2);
		lampes.add(lampe3);
		lampes.add(lampe4);
		separator = new JSeparator();
		separator.setBounds(554, 23, 7, 412);

		separator.setOrientation(SwingConstants.VERTICAL);






		/** Control buttons **/

		bPauseResume = new JToggleButton(iconButtonPause);
		bPauseResume.setBounds(663, 356, 61, 57);

		
		
	
		bSetup = new JButton(iconButtonSetup);
		bSetup.setBounds(823, 356, 61, 57);




		addwslabel = new JLabel("Address of Webservice :");
		addwslabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addwslabel.setBounds(571, 39, 165, 14);


		ipOfWs = new JTextField();
		ipOfWs.setBounds(758, 38, 126, 20);
		ipOfWs.setColumns(10);


		lblAddressOftechno = new JLabel("Address of Gateway :");
		lblAddressOftechno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddressOftechno.setBounds(571, 71, 220, 14);


		ipOfTechno = new JTextField();
		ipOfTechno.setBounds(758, 69, 126, 20);
		ipOfTechno.setColumns(10);



		chooseModeLabel = new JLabel("Choose your mode :");
		chooseModeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseModeLabel.setBounds(571, 116, 165, 14);


		speedSlider = new JSlider();
		speedSlider.setBounds(558, 237, 326, 23);
		speedSlider.setMaximum(1500);
		speedSlider.setMinimum(500);
		speedSlider.setValue(800);
		speedSlider.setInverted(true);


		controllabel = new JLabel("Connections :");
		controllabel.setForeground(Color.ORANGE);
		controllabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		controllabel.setBounds(662, 8, 148, 23);


		lblVisualization = new JLabel("Visualisation :");
		lblVisualization.setForeground(Color.ORANGE);
		lblVisualization.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVisualization.setBounds(191, 12, 171, 14);


		techSpinner = new JComboBox();
		techSpinner.setBounds(758, 116, 126, 20);
		techSpinner.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"SIMU", "KNX" }));

		speedlabel = new JLabel("Speed Control :");
		speedlabel.setForeground(Color.ORANGE);
		speedlabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		speedlabel.setBounds(662, 199, 137, 26);


		speedSeparator = new JSeparator();
		speedSeparator.setBounds(554, 187, 340, 14);


		bConnect = new JButton("Connect");
		bConnect.setBounds(571, 153, 89, 23);

		bDisconnect = new JButton("Disconnect");
		bDisconnect.setBounds(758, 153, 89, 23);


		lblSlow = new JLabel("Slow");
		lblSlow.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSlow.setBounds(568, 259, 46, 14);


		lblFast = new JLabel("Fast");
		lblFast.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFast.setBounds(838, 259, 46, 14);


		seqSeparator = new JSeparator();
		seqSeparator.setBounds(554, 304, 340, 14);


		bStop = new JButton(iconButtonStop);
		bStop.setBounds(745, 356, 57, 57);
		bStart = new JButton(iconButtonAdd);
		bStart.setBounds(578, 356, 61, 57);


		sequenceControlLabel = new JLabel("Sequence Control :");
		sequenceControlLabel.setForeground(Color.ORANGE);
		sequenceControlLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		sequenceControlLabel.setBounds(662, 314, 165, 31);	

		
		connectionlabel = new JLabel("No connection ");
		connectionlabel.setBounds(10, 447, 256, 14);
		
		useBth = new JCheckBox("Use Bluetooth Indicator");
		useBth.setFont(new Font("Tahoma", Font.ITALIC, 12));
		useBth.setBounds(304, 9, 171, 23);
		}

	/** Fonction permettant l'ajout des composnat a l'IHM **/
	
	public void addComponents(){
		getContentPane().add(lampe1);
		getContentPane().add(lampe2);
		getContentPane().add(lampe3);
		getContentPane().add(lampe4);
		getContentPane().add(separator);
		getContentPane().add(bPauseResume);
		getContentPane().add(addwslabel);
		getContentPane().add(ipOfWs);
		getContentPane().add(lblAddressOftechno);
		getContentPane().add(ipOfTechno);
		getContentPane().add(bSetup);
		getContentPane().add(chooseModeLabel);
		getContentPane().add(speedSlider);
		getContentPane().add(controllabel);
		getContentPane().add(lblVisualization);
		getContentPane().add(techSpinner);
		getContentPane().add(speedlabel);
		getContentPane().add(speedSeparator);
		getContentPane().add(bConnect);
		getContentPane().add(bDisconnect);
		getContentPane().add(lblSlow);
		getContentPane().add(lblFast);
		getContentPane().add(seqSeparator);
		getContentPane().add(bStop);
		getContentPane().add(bStart);
		getContentPane().add(sequenceControlLabel);
		getContentPane().add(connectionlabel);
		getContentPane().add(useBth);
	}
	
	/** Fonction permettant l'ajout de listener auxx composants **/
	public void addListeners(){
		
		
	SetupListener sList=new SetupListener();
	bSetup.addActionListener(sList);
	
	ConnectionListener cList=new ConnectionListener(this);
	bConnect.addActionListener(cList);
	bDisconnect.addActionListener(cList);

	
	final Sequence seq=new Sequence(this);
	
	bStart.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		seq.start(getTechnoAdd(),"1-2-3-4");
		}
	});
	
	bStop.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		seq.stop(getTechnoAdd());
		}
		});
	
	bPauseResume.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		seq.pauseResume(getTechnoAdd());
		}
		});
	
	TechnoListener tList=new TechnoListener();
	techSpinner.addActionListener(tList);
	
	SpeedListener sL=new SpeedListener(this);
	speedSlider.addChangeListener(sL);
	BthListener bl=new BthListener(this);
	useBth.addActionListener(bl);
	
	}
	
	
	public  String getTechnoAdd(){
		return ipOfTechno.getText();
	}
	
	public  String getChosenTechno() {
		String technologie = (String) techSpinner.getSelectedItem();
		return technologie;
	}
	
	public String getWsAdd(){
		return ipOfWs.getText();
	}
	
	
	public  void changeConnectText(String s){
		connectionlabel.setText(s);
	}


	public static String getPreviousTechno() {
		// TODO Auto-generated method stub
		return null;
	}


	public  void allOff() {
    for(int i=0;i<lampes.size();i++){
	lampes.get(i).turnOff();	
	}}


	public  void turnOn(int i) {
		lampes.get(i).turnOn();
	}


	public void turnOff(int i) {
		lampes.get(i).turnOff();	
		
	}


	public  void inPause() {
		bPauseResume.setIcon(iconButtonStart);
	}
	public  void inResume() {
		bPauseResume.setIcon(iconButtonPause);
	}
}

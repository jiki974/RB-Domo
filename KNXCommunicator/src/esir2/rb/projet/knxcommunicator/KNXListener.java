package esir2.rb.projet.knxcommunicator;

import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.dptxlator.DPT;
import tuwien.auto.calimero.dptxlator.DPTXlator;
import tuwien.auto.calimero.dptxlator.TranslatorTypes;
import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.link.event.NetworkLinkListener;

public class KNXListener implements NetworkLinkListener {

	//Processor proc;
	String address;
	String group;

	private boolean isdestination=false;
	private boolean issource=false;
	private String value="";

	private 	DPTXlator trs;

	public KNXListener(String gp){
		//this.proc=p;
		//this.address=add;
		this.group=gp;
	}

	@Override
	public void indication(FrameEvent frame) {

		String destination=((tuwien.auto.calimero.cemi.CEMILData)frame.getFrame()).getDestination().toString();
		String source=((tuwien.auto.calimero.cemi.CEMILData)frame.getFrame()).getSource().toString();




		if(destination.contains(group)) {
			value=getBooleanState(((tuwien.auto.calimero.cemi.CEMILData)frame.getFrame()).getPayload());
			System.out.println(value);
			isdestination=true;
			issource=false;
			//proc.resumeSequence(address);	
		}

		if(source.contains(group)) {


			issource=true;
			isdestination=false;
			//proc.resumeSequence(address);	
		}

		/*else if (target.contains("0/2/1")){
			proc.pauseSequence(address);
		}*/
	}

	@Override
	public void linkClosed(CloseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void confirmation(FrameEvent arg0) {
		// TODO Auto-generated method stub

	}


	public boolean isDestination(){
		return isdestination;
	}


	public boolean isSource(){
		return issource;
	}


	public String getGroup(){
		return group;
	}


	public void reset(){
		isdestination=false;
		issource=false;
	}

	public String getValue() {
		return value;
	}

	public String getBooleanState(byte[] tdpu){
		String ret="";
		DPT dpt = new DPT("1.001", "Boolean", "0","1", "");

		try {
			trs = TranslatorTypes.createTranslator(dpt);
		} catch (KNXException e) {
			System.out.println(e.getMessage());
		}
		trs.setData(tdpu);
		trs.setAppendUnit(true);
		String[] values=trs.getAllValues();
		value=values[1];
		
		if(value.equals("on"))ret="1";
		else ret="0";
		return ret;
	}

}

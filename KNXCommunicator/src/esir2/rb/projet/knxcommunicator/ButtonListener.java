package esir2.rb.projet.knxcommunicator;

import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.link.event.NetworkLinkListener;
import esir2.rb.projet.processor.Processor;

public class ButtonListener implements NetworkLinkListener {
	Processor proc;
	public ButtonListener(Processor p){
		this.proc=p;
	}

	@Override
	public void indication(FrameEvent frame) {
		String target=((tuwien.auto.calimero.cemi.CEMILData)frame.getFrame()).getDestination().toString();

		if( target.contains("0/2/0")) {
			System.out.println(target);
			//proc.resumeSequence();
		}
		else if (target.contains("0/2/1")){
			System.out.println(target);
			System.out.println("on arrete la séquence");
			//proc.pauseSequence();
		}
	}

	@Override
	public void linkClosed(CloseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void confirmation(FrameEvent arg0) {
		// TODO Auto-generated method stub

	}

}

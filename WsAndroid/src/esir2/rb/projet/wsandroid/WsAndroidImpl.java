package esir2.rb.projet.wsandroid;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import esir2.rb.projet.processor.Processor;

@WebService( 
		endpointInterface = "esir2.rb.projet.wsandroid.WsAndroid", 
		serviceName = "wsandroid")
//@HandlerChain(file = "handler-chain.xml") 
public class WsAndroidImpl implements WsAndroid {	
	    Processor proc;
	    public  WsAndroidImpl(Processor p){
	    	proc=p;
	    }
		public String sayHello(String name) {
			return "Hey hey " + name;
		}
	
		public void connect(String add, int techno){
		proc.connect(add,techno);
		}// entame une connection avec une addresse et une technologie ( knx ou bacnet)
		
		public void disconnect(String add){
			proc.disconnect(add);
		}//ferme la connection
		
		public void startSequence(String add,String seq){
		proc.startSequence(add,seq);
		}//demarre une séquence
		public void resumeSequence(String add){
		proc.resumeSequence(add);	
			
		}//reprend une séquence
		public void pauseSequence(String add){
			proc.pauseSequence(add);
			// met en pause une séquence
		}
		public void stopSequence(String add){
		proc.stopSequence(add);
		System.out.println("stopsequence");
			// arrete une séquence;
		}
		public boolean isConnected(String add){
			return proc.isConnected(add);
		
		}
		@Override
		public String getConnections() {
			// TODO Auto-generated method stub
			return proc.getConnections();
		}
		@Override
		public String getSequences() {
			// TODO Auto-generated method stub
		return proc.getSequences();
		}
		@Override
		public void setSequenceSpeed(String add, int speed) {
			proc.setSequenceSpeed(add, speed);
		}
		@Override
		public int getSequenceSpeed(String add) {
			
			return proc.getSequenceSpeed(add);
		}
		@Override
		public String getState(String add, String addgp) {
			// TODO Auto-generated method stub
			return proc.getState(add, addgp);
		}
		public void writeOn(String add,int lampe) {
	    proc.writeOn(add, lampe);
		}
	
		public void writeOff(String add,int lampe) {
			proc.writeOff(add, lampe);
		}



}

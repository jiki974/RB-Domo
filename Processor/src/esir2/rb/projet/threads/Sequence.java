package esir2.rb.projet.threads;
import esir2.rb.projet.communicator.Communicator;
import esir2.rb.projet.objets.LampList;
import esir2.rb.projet.util.Util;



public class Sequence implements Runnable {

	private boolean end=false;
	private boolean launched=false;
	private static LampList<Integer>seq;
	private Communicator comm;
	private String sequence;
	private int speed=800;
	private String add;
	
	
	private volatile Thread seqThread;
	private boolean threadSuspended=false;

	public Sequence(String a,Communicator c,String seq){
		add=a;
		comm=c;
		sequence=seq;
		makeSequence(seq);
	}

	public void run() {
		Thread thisThread = Thread.currentThread();
		seqThread=Thread.currentThread();
		launched=true;
		allOff();

		while(seqThread==thisThread){

			for(int i=0;i<4;i++){

				try {

					if(launched){
						comm.writeOn(add,seq.get(i));
						comm.writeOn(add,seq.getNext(i));
						comm.writeOff(add,seq.getPrevious(i));}
						thisThread.sleep(speed);

					synchronized(this) {
						while (threadSuspended && seqThread==thisThread)
							wait();
					}


				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());}
			}

		}
	}

	public synchronized void stop(){
		if(launched || threadSuspended){
			seqThread=null;
			launched=false;
			allOff();}

	}

	private void makeSequence(String s){

		seq=new LampList<Integer>();
		String[] sp=s.split("-");
		for(int i=0;i<sp.length;i++) seq.add(Integer.valueOf(sp[i]));
	}

	public boolean isLaunched(){
		return launched;
	}

	public void allOff(){
		try{
			comm.allOff(add);
		}
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public  synchronized void pause() {
		launched=false;
		threadSuspended = true;

	
	}


	public synchronized void resume() {
		launched=true;
		threadSuspended = false;
		notify();
		// TODO Auto-generated method stub

	}

	public String getSequence(){
		return sequence;	
	}

	public void setSpeed(int s) {
		speed=s;		
	}

	public int getSpeed(){
		return speed;
	}

}

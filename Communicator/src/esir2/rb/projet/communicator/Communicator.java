package esir2.rb.projet.communicator;


public interface Communicator {
	
	public boolean openConnection(String ipadd) throws Exception;
	public void closeConnection();
	public void writeOn(int adress_nb) throws Exception;;
	public void writeOff(int adress_nb) throws Exception;;
	public boolean readState(int adress_nb) throws Exception;
	public void allOff() throws Exception;	
	public boolean isConnected();
	public String getTechno();

}

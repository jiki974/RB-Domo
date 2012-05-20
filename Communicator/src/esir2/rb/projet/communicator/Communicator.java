package esir2.rb.projet.communicator;


public interface Communicator {
	
	public boolean openConnection(String ipadd) throws Exception;
	public void closeConnection(String ipadd);
	public void writeOn(String ipadd,int adress_nb) throws Exception;;
	public void writeOff(String ipadd,int adress_nb) throws Exception;;
	public boolean readState(String ipadd,int adress_nb) throws Exception;
	public void allOff(String ipadd) throws Exception;	
	public boolean isConnected(String ipadd);
	public String getTechno();
	public void setListener(String ipadd,String gpadd);	
	public void resetListener(String ipadd,String gpadd);
	public void removeListener(String ipadd,String gpadd);
	public boolean isSource(String ipadd,String gpadd);
	public boolean isDestination(String ipadd,String gpadd);
	public String getValue(String ipadd,String gpadd,int nb);

	
}

package esir2.rb.projet.objets;

public class Connection {
	
	
	/** Declarations des variables **/
	
	private String add;
	private int techno;
	private boolean open=false;

	
	
	public Connection(String i,String a,int t){
		add=a;
		techno=t;
	}

	public String getAdd(){
	return add;	
	}
	
	public int getTechno(){
	return techno;	
	}
	
	public void openConnection(){
	open=true;
	}
	
	public boolean isOpen(){
		return open;
	}
}

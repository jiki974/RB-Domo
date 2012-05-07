package esir2.rb.projet.wsandroid;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService

public interface WsAndroid {
	String sayHello( @WebParam(name="name") String name );
	void connect (@WebParam(name="add") String add ,@WebParam(name="techno") int techno);// entame une connection avec une addresse et une technologie ( knx ou bacnet)
	void disconnect(@WebParam(name="add") String add );//ferme la connection
	void startSequence(@WebParam(name="add") String add,@WebParam (name="nameseq") String nameseq);//demarre une séquence
	void setSequenceSpeed (@WebParam(name="add") String add ,@WebParam(name="speed") int speed);
	int getSequenceSpeed(@WebParam(name="add") String add );
	void resumeSequence(@WebParam(name="add") String add );//reprend une séquence
	void pauseSequence(@WebParam(name="add") String add );// met en pause une séquence
	void stopSequence(@WebParam(name="add") String add );// arrete une séquence;
	boolean isConnected(@WebParam(name="add") String add );// indique si 'lon est connecté à la techno 
	String getState(@WebParam(name="add") String add,@WebParam(name="addgp") String addgp);
	String getConnections();
	String getSequences();



}

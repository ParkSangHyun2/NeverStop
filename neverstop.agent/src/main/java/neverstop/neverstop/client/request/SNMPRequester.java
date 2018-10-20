package neverstop.neverstop.client.request;

import java.io.IOException;
import java.util.List;

import org.snmp4j.smi.OID;

import neverstop.neverstop.client.SNMPClient;
import neverstop.neverstop.client.domain.type.StateOID;

public class SNMPRequester implements NeverStopRequester{
	//
	private final String DEFAULT_ADDRESS = "udp:127.0.0.1/9999";
	private final String ADDRESS_PREFIX = "udp:";
	private final String DEFAULT_SPLIT= "/";
	
	private String address;
	
	private SNMPClient client;
	
	public SNMPRequester() {
		//
		this.address = DEFAULT_ADDRESS;
	}
	
	public SNMPRequester(String address,String port) {
		//
		this.address = ADDRESS_PREFIX+address+DEFAULT_SPLIT+port;
	}
	
	public void connect() {
		//
		client = new SNMPClient(address);
	}
	
	public String getState(StateOID stateOID) {
		//
		String oid = stateOID.getOID();
		String result = null;
		try {
			result = client.getAsString(new OID(oid));
		} catch (IOException e1) {
			//
			e1.printStackTrace();
		}
		return result;
	}
	
	public List<String> getStates(OID[] oids){
		//
		List<String> states;
		states = client.getTableAsStrings(oids).get(0);
		return states;
	}
	
	public void disconnect() throws IOException {
		//
		client.stop();
	}
	
}

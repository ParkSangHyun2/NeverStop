package neverstop.neverstop.client.request;

import java.util.List;

import org.snmp4j.smi.OID;

import neverstop.neverstop.client.domain.type.StateOID;

public interface NeverStopRequester {
	//
	String getState(StateOID stateOID);
	
	List<List<String>> getStates(OID[] oids);
}

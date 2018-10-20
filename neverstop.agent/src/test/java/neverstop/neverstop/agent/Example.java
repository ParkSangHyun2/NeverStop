package neverstop.neverstop.agent;

import java.io.IOException;
import java.util.List;

import org.snmp4j.smi.OID;

import neverstop.neverstop.client.domain.type.StateOID;
import neverstop.neverstop.client.request.SNMPRequester;

public class Example {
 //
	public static void main(String[] args) {
		//
		SNMPRequester requester = new SNMPRequester();
		requester.connect();
		List<List<String>> results = requester.getStates(new OID[]{
				new OID(StateOID.InterFace.getOID() + StateOID.ErrorHandler.getOID())
						});
		for(List<String> result : results) {
			for(String value : result ){
				System.out.println("RESULT : "+value);
			}

		}
		try {
			requester.disconnect();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
	}
}

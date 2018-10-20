package neverstop.neverstop.agent.domain;

import java.io.IOException;

import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;

import neverstop.neverstop.agent.domain.state.DeviceState;
import neverstop.neverstop.agent.domain.type.StateOID;
import neverstop.neverstop.agent.util.MOTableBuilder;

public class NeverStopAgent {
	//
	private final String DEFAULT_ADDRESS = "0.0.0.0/9999";
	private final String DEFAULT_SPLIT = "/";

	private SNMPAgent snmpAgent;
	private String address;

	public NeverStopAgent() {
		//
		this.address = DEFAULT_ADDRESS;
	}

	public NeverStopAgent(String address, String port) {
		//
		this.address = address + DEFAULT_SPLIT + port;
	}

	private void setupAgent() {
		//
		StateOID interfaceOID = StateOID.InterFace;

		try {
			snmpAgent = new SNMPAgent(address);

			MOTableBuilder builder = new MOTableBuilder(new OID(interfaceOID.getOID()))
					.addColumnType(SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_ONLY)
					.addColumnType(SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_ONLY)
					.addColumnType(SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_ONLY)
					.addColumnType(SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_ONLY)
					.addColumnType(SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_ONLY)
					//
					.addRowValue(new OctetString(DeviceState.POWERBALANCE))
					.addRowValue(new OctetString(DeviceState.PROCESSOR))
					.addRowValue(new OctetString(DeviceState.MEMORY))
					.addRowValue(new OctetString(DeviceState.NETBANDWIDTH))
					.addRowValue(new OctetString(DeviceState.ERRORHANDLER));

			snmpAgent.registerManagedObject(builder.build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start(int delayMilisecond) throws InterruptedException {
		//
		setupAgent();
		try {
			snmpAgent.start();
			System.out.println("Started..");
			while (true) {
				System.out.println("waiting..");
				Thread.sleep(delayMilisecond);
			}
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public void stop() {
		snmpAgent.stop();
	}
}

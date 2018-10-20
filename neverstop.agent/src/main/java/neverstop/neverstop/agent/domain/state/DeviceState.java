package neverstop.neverstop.agent.domain.state;

public class DeviceState {
	//
	public static String POWERBALANCE = "90%";
	public static String PROCESSOR = "25%";
	public static String MEMORY = "30%";
	public static String NETBANDWIDTH = "3";
	public static String ERRORHANDLER = "LOW Battery...10%";
	
	public static String getPOWERBALANCE() {
		return POWERBALANCE;
	}
	public static String getPROCESSOR() {
		return PROCESSOR;
	}
	public static String getMEMORY() {
		return MEMORY;
	}
	public static String getNETBANDWIDTH() {
		return NETBANDWIDTH;
	}
	public static String getERRORHANDLER() {
		return ERRORHANDLER;
	}
	
	
}

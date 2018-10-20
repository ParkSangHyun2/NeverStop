package neverstop.neverstop.client.domain.type;

public enum StateOID {
	//
	InterFace(".1.3.6.1.4.1.50582.1.2.2"),
	PowerBalance(".1"),
	Processor(".2"),
	Memory(".3"),
	NetBandWidth(".4"),
	ErrorHandler(".5");
	
	private String oid;
	
	StateOID(String oid) {
		//
		this.oid = oid;
	}
	
	public String getOID() {
		return oid;
	}
}

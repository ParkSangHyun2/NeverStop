package neverstop.neverstop.client.domain.type;

public enum StateOID {
	//
	InterFace(".1.3.6.1.4.1.50582.1.2.2"),
	DeviceId(".1"),
	PowerBalance(".2"),
	Processor(".3"),
	Memory(".4"),
	NetBandWidth(".5"),
	ErrorHandler(".6");

	private String oid;

	StateOID(String oid) {
		//
		this.oid = oid;
	}

	public String getOID() {
		return oid;
	}
}

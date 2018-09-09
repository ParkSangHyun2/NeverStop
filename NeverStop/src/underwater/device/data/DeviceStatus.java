package underwater.device.data;

public class DeviceStatus {
	private byte id;
	private byte method;
	private byte memory;
	private byte powerBalence;

	public DeviceStatus() {
		//
		id = 1;
		method = 2;
		memory = 1;
		powerBalence = 0;
	}
	
	public DeviceStatus(byte id, byte method, byte memory, byte powerBalence) {
		//
		this.id = id;
		this.method = method;
		this.memory = memory;
		this.powerBalence = powerBalence;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public byte getMethod() {
		return method;
	}

	public void setMethod(byte method) {
		this.method = method;
	}

	public byte getMemory() {
		return memory;
	}

	public void setMemory(byte memory) {
		this.memory = memory;
	}

	public byte getPowerBalence() {
		return powerBalence;
	}

	public void setPowerBalence(byte powerBalence) {
		this.powerBalence = powerBalence;
	}
	
	public byte[] getSystemInfo() {
		//
		byte[] systemInfo = {
				this.id,
				this.memory,
				this.method,
				this.powerBalence
				};
		return systemInfo;
	}
}

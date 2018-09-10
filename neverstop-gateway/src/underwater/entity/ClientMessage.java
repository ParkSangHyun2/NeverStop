package underwater.entity;

public class ClientMessage {
	//
	private String deviceId;
	private String State;
	private String PowerBalence;
	private String cpu;
	private String memory;
	private String responseTime;
	
	public ClientMessage() {
		
	}

	public ClientMessage(String deviceId, String state, String powerBalence, String cpu, String memory,
			String responseTime) {
		super();
		this.deviceId = deviceId;
		State = state;
		PowerBalence = powerBalence;
		this.cpu = cpu;
		this.memory = memory;
		this.responseTime = responseTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getPowerBalence() {
		return PowerBalence;
	}

	public void setPowerBalence(String powerBalence) {
		PowerBalence = powerBalence;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	
}

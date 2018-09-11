package underwater.util;

@Deprecated
public class RequestMessage {
	//
	private String methodName;
	private int deviceId;
	private int interval;
	
	public RequestMessage() {
		
	}
	
	public RequestMessage(String methodName, int deviceId, int interval) {
		super();
		this.methodName = methodName;
		this.deviceId = deviceId;
		this.interval = interval;
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	
}

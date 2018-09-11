package underwater.entity;

public class NeverStopDevice {
	//
	public byte deviceId;
	public byte power;
	public byte reportInterval;
	public byte bettaryStatus;
	public byte cpuStatus;
	public byte memoryStatus;
	
	private NeverStopDevice(byte deviceId, byte power, byte reportInterval, byte bettaryStatus, byte cpuStatus,
			byte memoryStatus) {
		this.deviceId = deviceId;
		this.power = power;
		this.reportInterval = reportInterval;
		this.bettaryStatus = bettaryStatus;
		this.cpuStatus = cpuStatus;
		this.memoryStatus = memoryStatus;
	}

	public static NeverStopDevice getInstance(byte deviceId, byte power, byte reportInterval, byte bettaryStatus, byte cpuStatus, byte memoryStatus) {
		//
		return (new NeverStopDevice(deviceId, power, reportInterval, bettaryStatus, cpuStatus, memoryStatus));
	}
	
	public byte[] getDeviceStatus() {
		//
		byte[] datas = {deviceId, power, reportInterval, bettaryStatus, cpuStatus, memoryStatus};
		return datas;
	}

	public byte getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(byte deviceId) {
		this.deviceId = deviceId;
	}

	public byte getPower() {
		return power;
	}

	public void setPower(byte power) {
		this.power = power;
	}

	public byte getReportInterval() {
		return reportInterval;
	}

	public void setReportInterval(byte reportInterval) {
		this.reportInterval = reportInterval;
	}

	public byte getBettaryStatus() {
		return bettaryStatus;
	}

	public void setBettaryStatus(byte bettaryStatus) {
		this.bettaryStatus = bettaryStatus;
	}

	public byte getCpuStatus() {
		return cpuStatus;
	}

	public void setCpuStatus(byte cpuStatus) {
		this.cpuStatus = cpuStatus;
	}

	public byte getMemoryStatus() {
		return memoryStatus;
	}

	public void setMemoryStatus(byte memoryStatus) {
		this.memoryStatus = memoryStatus;
	}
	
	
}

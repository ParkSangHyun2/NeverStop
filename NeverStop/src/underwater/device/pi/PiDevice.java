package underwater.device.pi;

import underwater.device.data.DeviceStatus;

public class PiDevice {
	//
	private DeviceStatus deviceData;
	
	public PiDevice() {
		//
		deviceData = new DeviceStatus();
	}
	
	public byte[] getDeviceStatus() {
		//
		byte[] datas = deviceData.getSystemInfo();
		return datas;
	}
}

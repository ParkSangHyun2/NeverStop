package underwater.device.pi;

import underwater.device.data.DeviceStatus;

public class PiDevice {
	//
	private DeviceStatus deviceData;
	
	public PiDevice(byte deviceId) {
		//
		deviceData = new DeviceStatus(deviceId);
	}
	
	public byte[] getDeviceStatus() {
		//
		byte[] datas = deviceData.getSystemInfo();
		return datas;
	}
}

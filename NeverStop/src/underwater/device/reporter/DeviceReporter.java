package underwater.device.reporter;

import java.io.IOException;

import underwater.device.pi.PiDevice;

public class DeviceReporter {
	//
	private ReportDispatcher dispatcher;
	private PiDevice device;
	private final String IP = "127.0.0.1";
	private final int PORT = 9999;
	
	public DeviceReporter(byte deviceId) {
		dispatcher = ReportDispatcher.getInstance(IP, PORT);
		device = new PiDevice(deviceId);
	}
	
	public DeviceReporter(String ip, int port, byte deviceId) {
		//
		dispatcher = ReportDispatcher.getInstance(ip, port);
		device = new PiDevice(deviceId);
	}
	
	public void reportData() throws IOException {
		//
		byte[] deviceData = device.getDeviceStatus();
		dispatcher.dispatchVoid(deviceData);
	}
}


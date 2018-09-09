package underwater.device.reporter;

import java.io.IOException;

import underwater.device.pi.PiDevice;

public class DeviceReporter {
	//
	private ReportDispatcher dispatcher;
	private PiDevice device;
	private String ip;
	private int port;
	
	public DeviceReporter(String ip, int port) {
		//
		dispatcher = ReportDispatcher.getInstance(ip, port);
		device = new PiDevice();
	}
	
	public void reportData() throws IOException {
		//
		byte[] deviceData = device.getDeviceStatus();
		dispatcher.dispatchVoid(deviceData);
	}
}


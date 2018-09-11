package underwater.reporter;

import java.io.IOException;

import underwater.entity.NeverStopDevice;

public class DeviceReporter {
	//
	private NeverStopDevice device;
	private ReportDispatcher dispatcher;
	private final String IP = "127.0.0.1";
	private final int PORT = 8000;
	
	public DeviceReporter(NeverStopDevice device) {
		this.device = device;
		dispatcher = ReportDispatcher.getInstance(IP, PORT);
	}
	
	public DeviceReporter(String ip, int port, NeverStopDevice device) {
		//
		this.device = device;
		dispatcher = ReportDispatcher.getInstance(ip, port);
	}
	
	public void reportData() throws IOException {
		//
		byte[] deviceData = device.getDeviceStatus();
		dispatcher.dispatchVoid(deviceData);
	}
}


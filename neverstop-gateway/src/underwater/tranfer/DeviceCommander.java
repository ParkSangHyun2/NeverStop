package underwater.tranfer;

import java.io.IOException;

public class DeviceCommander {
	//
	private GatewayDispatcher dispatcher;
	private final String IP = "127.0.0.1";
	
	public DeviceCommander(int deviceId) {
		dispatcher = GatewayDispatcher.getInstance(IP, Integer.parseInt("999"+String.valueOf(deviceId)));
	}
	
	public DeviceCommander(String ip, int port) {
		//
		dispatcher = GatewayDispatcher.getInstance(ip, port);
	}
	
	public void reportData(byte[] interval) throws IOException {
		//
		dispatcher.dispatchVoid(interval);
	}
}


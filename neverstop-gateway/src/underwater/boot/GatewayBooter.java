package underwater.boot;

import underwater.react.DeviceReactor;

public class GatewayBooter {
	//
	public static void main(String[] args) {
		DeviceReactor reactor = new DeviceReactor();
		reactor.run();
	}
}

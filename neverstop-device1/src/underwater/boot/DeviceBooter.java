package underwater.boot;

import underwater.entity.NeverStopDevice;
import underwater.listener.DeviceReactor;
import underwater.reporter.timer.DeviceTimer;

public class DeviceBooter {
	//
	public static void main(String[] args) {
		//
		NeverStopDevice device = NeverStopDevice.getInstance((byte)1, (byte)1, (byte)0, (byte)0, (byte)0, (byte)0);
		
		DeviceTimer timer = new DeviceTimer(1, 1, device);
		timer.runDevice();
		System.out.println("Device Booted!");
		
		DeviceReactor reactor = new DeviceReactor(device);
		reactor.run();
		System.out.println("Device Listened!");
	}
}

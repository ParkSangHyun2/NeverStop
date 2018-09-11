package kr.co.nextree.boot;

import kr.co.nextree.entity.NeverStopDevice;
import kr.co.nextree.listener.DeviceReactor;
import kr.co.nextree.reporter.timer.DeviceTimer;

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

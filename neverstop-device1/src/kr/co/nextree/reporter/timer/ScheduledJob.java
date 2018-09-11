package kr.co.nextree.reporter.timer;

import java.io.IOException;
import java.util.TimerTask;

import kr.co.nextree.entity.NeverStopDevice;
import kr.co.nextree.reporter.DeviceReporter;

public class ScheduledJob extends TimerTask {
	//
	private DeviceReporter reporter;
	private NeverStopDevice device;
	
	public ScheduledJob(NeverStopDevice device) {
		//
		this.device = device;
		
	}
	public void run() {
		//
		try {
			reporter = new DeviceReporter(device);
			if(device.getPower() != 0) {
				reporter.reportData();	
			}else {
				System.out.println("Device OFF");
			}
			
		} catch (IOException e) {
			//
			new RuntimeException(e);
		}
	}
}

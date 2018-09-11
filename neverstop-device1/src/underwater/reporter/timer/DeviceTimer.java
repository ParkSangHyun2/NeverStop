package underwater.reporter.timer;

import java.util.Timer;

import underwater.entity.NeverStopDevice;

public class DeviceTimer {
	//
	private ScheduledJob job;
	private Timer jobScheduler;

	private long delay;
	private long period;

	public DeviceTimer(long delay, long period, NeverStopDevice device) {
		//
		this.job = new ScheduledJob(device);
		this.jobScheduler = new Timer();

		this.delay = delay * 1000;
		this.period = (period * 1000);
	}

	public void runDevice() {
		jobScheduler.scheduleAtFixedRate(job, delay, period);
	}

	public void shutDownDevice() {
		jobScheduler.cancel();
	}
}

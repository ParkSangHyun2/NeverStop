package underwater.subagent;

public class SubDeviceManager implements DeviceManager{

	@Override
	public void awake() {
		//
		System.out.println("NEVERSTOP ON");
	}

	@Override
	public void sleep() {
		//
		System.out.println("NEVERSTOP OFF");
	}

}

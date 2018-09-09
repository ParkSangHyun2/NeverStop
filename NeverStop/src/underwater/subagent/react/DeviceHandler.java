package underwater.subagent.react;

import java.io.IOException;
import java.net.Socket;

import underwater.device.reporter.DeviceReporter;
import underwater.subagent.DeviceManager;
import underwater.subagent.SubDeviceManager;
import underwater.util.DeviceIOException;
import underwater.util.SocketWorker;

public class DeviceHandler implements Runnable {
	//
	private DeviceReporter deviceReporter;
	private DeviceManager deviceManager;
	private SocketWorker socketWorker;

	public DeviceHandler(Socket clientSocket) {
		//
		this.socketWorker = new SocketWorker(clientSocket);
	}

	@Override
	public void run() {
		//
		try {
			byte[] datas = socketWorker.read();
			byte deviceId = datas[0];
			byte devicePower = datas[1];

			devicePowerControl(deviceId, devicePower);

			socketWorker.closeSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void devicePowerControl(byte deviceId, byte devicePower) {
		//
		deviceReporter = new DeviceReporter(deviceId);
		this.deviceManager = new SubDeviceManager();
		
		switch(devicePower) {
		case 0:
			deviceManager.sleep();
			break;
		case 1:
			deviceManager.awake();
			break;
			default:
				//
		}
		
		try {
			deviceReporter.reportData();
		} catch (IOException message) {
			//
			new DeviceIOException(message);
		}
	}

}

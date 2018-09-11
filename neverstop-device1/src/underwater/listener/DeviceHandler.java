package underwater.listener;

import java.io.IOException;
import java.net.Socket;

import underwater.entity.NeverStopDevice;
import underwater.util.SocketWorker;

public class DeviceHandler implements Runnable {
	//
	private SocketWorker socketWorker;
	private NeverStopDevice device;

	public DeviceHandler(Socket clientSocket, NeverStopDevice device) {
		//
		this.device = device;
		this.socketWorker = new SocketWorker(clientSocket);
	}

	@Override
	public void run() {
		//
		try {
			device.setReportInterval(socketWorker.read()[0]);
			socketWorker.closeSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setInterval() {
		//
	}

}

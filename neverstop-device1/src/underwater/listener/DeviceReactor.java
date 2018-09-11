package underwater.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import underwater.entity.NeverStopDevice;
import underwater.util.ReactFailException;

public class DeviceReactor {
	private int servicePort;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private NeverStopDevice device;

	public DeviceReactor(NeverStopDevice device) {
		//
		this.device = device;
		this.servicePort = Integer.parseInt("900" + String.valueOf(device.getDeviceId()));
		pool = Executors.newCachedThreadPool();
	}

	private void initServerSocket() {
		//
		try {
			serverSocket = new ServerSocket(servicePort);
		} catch (IOException e) {
			throw new ReactFailException(e.getMessage());
		}
	}

	public void run() {
		//
		this.initServerSocket();
		while (true) {
			//
			Socket clientSocket = null;

			try {
				synchronized (serverSocket) {
					System.out.println("Listening..");
					clientSocket = serverSocket.accept();
				}

				pool.execute(new DeviceHandler(clientSocket, device));
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}

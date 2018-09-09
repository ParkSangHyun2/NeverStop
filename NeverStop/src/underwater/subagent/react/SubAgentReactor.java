package underwater.subagent.react;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import underwater.util.ReactFailException;

public class SubAgentReactor {
	//
	private int servicePort;
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public SubAgentReactor() {
			//
			this.servicePort = 9999;
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
					System.out.println("Connecting..");
					clientSocket = serverSocket.accept();
				}

				pool.execute(new DeviceHandler(clientSocket));
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}

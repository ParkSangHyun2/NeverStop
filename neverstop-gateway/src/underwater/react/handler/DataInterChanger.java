package underwater.react.handler;

import java.net.Socket;

import underwater.util.SocketWorker;

public class DataInterChanger implements Runnable {
	//
	private SocketWorker socketWorker;

	public DataInterChanger(Socket socket) {
		//
		this.socketWorker = new SocketWorker(socket);
	}

	public void interchangeData() {
		//
		try {
			byte[] datas = socketWorker.read();

			DataConverter dataHandler = null;
			dataHandler = new DataConverter();

			dataHandler.convertToSNMP(datas);

			if (response != null) {
				socketWorker.writeMessage(response.toJson());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		socketWorker.closeSocket();
	}

	@Override
	public void run() {
		//
		interchangeData();
	}
}

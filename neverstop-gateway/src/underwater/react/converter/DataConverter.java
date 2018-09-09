package underwater.react.converter;

import java.io.IOException;
import java.net.Socket;

import underwater.util.SocketWorker;

public class DataConverter implements Runnable{
	//
	private SocketWorker socketWorker;

	public DataConverter(Socket socket) {
		//
		this.socketWorker = new SocketWorker(socket);
	}

	public void route() {
		//
		byte[] datas = socketWorker.read();

		ClubServiceHandler serviceHandler = null;
		serviceHandler = new ClubServiceHandler();

		ResponseMessage response = serviceHandler.handle(request);
		try {
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
		route();
	}
}

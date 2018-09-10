package underwater.react.handler;

import java.io.IOException;
import java.net.Socket;

import underwater.agent.converter.DataConverter;
import underwater.agent.storage.MapStorage;
import underwater.entity.ClientMessage;
import underwater.util.SocketWorker;

public class DeviceDataHandler implements Runnable {
	//
	private SocketWorker socketWorker;

	public DeviceDataHandler(Socket socket) {
		//
		this.socketWorker = new SocketWorker(socket);
	}

	public void interchangeData() {
		//
		try {
			byte[] datas = socketWorker.read();
			System.out.println(datas[0]);

			ClientMessage message = DataConverter.convertToMessageFrom(datas);
			MapStorage.setMessage(Integer.parseInt(message.getDeviceId()), message);
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

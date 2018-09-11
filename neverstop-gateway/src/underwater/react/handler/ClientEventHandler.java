package underwater.react.handler;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import underwater.agent.storage.MapStorage;
import underwater.tranfer.DeviceCommander;
import underwater.util.ClientSocketWorker;
import underwater.util.RequestMessage;
import underwater.util.ClientMessage;

@Deprecated
public class ClientEventHandler implements Runnable {
	//
	private ClientSocketWorker socketWorker;

	public ClientEventHandler(Socket clientSocket) {
		//
		this.socketWorker = new ClientSocketWorker(clientSocket);
	}

	private String handleMessage(String json) {
		//
		RequestMessage message = new Gson().fromJson(json, RequestMessage.class);
		ClientMessage responseMessage = null;
		String methodName = message.getMethodName();
		int deviceId = message.getDeviceId();
		int interval = message.getInterval();
		byte[] byteInterval = {(byte)interval};
		
		switch(methodName) {
		case "get":
			responseMessage = MapStorage.getMessages().get(deviceId);
			break;
		case "set":
			DeviceCommander commander = new DeviceCommander(deviceId);
			try {
				commander.reportData(byteInterval);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			default:
				//--
		}
		
		return new Gson().toJson(responseMessage);
	}

	@Override
	public void run() {
		//
		String json = socketWorker.readMessage();
		String handledMessage = handleMessage(json);

		try {
			if (handledMessage != null) {
				socketWorker.writeMessage(handledMessage);
			}
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		socketWorker.closeSocket();
	}

}

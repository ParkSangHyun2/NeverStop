package underwater.react.handler;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import underwater.entity.ClientMessage;
import underwater.util.ClientSocketWorker;

public class ClientEventHandler implements Runnable {
	//
	private ClientSocketWorker socketWorker;

	public ClientEventHandler(Socket clientSocket) {
		//
		this.socketWorker = new ClientSocketWorker(clientSocket);
	}

	private String handleMessage(String json) {
		//
		ClientMessage = new Gson().fromJson(json, ClientMessage.class);
		return null;
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

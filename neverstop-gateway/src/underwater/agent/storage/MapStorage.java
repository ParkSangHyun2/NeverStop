package underwater.agent.storage;

import java.util.HashMap;
import java.util.Map;

import underwater.util.ClientMessage;

public class MapStorage {
	//
	public static Map<Integer, ClientMessage> messages = new HashMap<Integer, ClientMessage>();
	
	public static ClientMessage getMessage(int deviceId) {
		//
		return messages.get(deviceId);
	}
	
	public static Map<Integer, ClientMessage> getMessages(){
		return messages;
	}
	
	public static void setMessage(int deviceId, ClientMessage message) {
		messages.put(deviceId, message);
	}
}

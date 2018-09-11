package underwater.agent.storage;

import java.util.HashMap;
import java.util.Map;

import underwater.util.ResponseMessage;

public class MapStorage {
	//
	public static Map<Integer, ResponseMessage> messages = new HashMap<Integer, ResponseMessage>();
	
	public static ResponseMessage getMessage(int deviceId) {
		//
		return messages.get(deviceId);
	}
	
	public static Map<Integer, ResponseMessage> getMessages(){
		return messages;
	}
	
	public static void setMessage(int deviceId, ResponseMessage message) {
		messages.put(deviceId, message);
	}
}

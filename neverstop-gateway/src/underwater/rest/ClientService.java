package underwater.rest;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import underwater.agent.storage.MapStorage;
import underwater.tranfer.DeviceCommander;
import underwater.util.ClientMessage;

@RestController
public class ClientService {
	//
	@GetMapping("/device/status")
	public Map<Integer, ClientMessage> getDeviceState() {
		//
		return MapStorage.getMessages();
	}
	
	@PutMapping("/device/interval/{deviceId}/{value}")
	public void setDeviceInterval(@PathVariable int deviceId, @PathVariable int value) {
		//
		DeviceCommander commander = new DeviceCommander(deviceId);
		try {
			byte[] byteInterval = {(byte)value};
			commander.reportData(byteInterval);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}
	
	
}

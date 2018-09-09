package underwater.boot;

import java.io.IOException;

import underwater.subagent.react.SubAgentReactor;

public class DeviceBooter {
	//
	public static void main(String[] args) {
		//
		// DEVICE ID ( 0 ~ 7 )
		SubAgentReactor reactor = new SubAgentReactor();
		reactor.run();

	}
}

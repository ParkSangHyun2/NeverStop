package underwater.boot;

import underwater.react.GatewayReactor;

@Deprecated
public class GatewayBooter {
	//
	public static void main(String[] args) {
		GatewayReactor reactor = new GatewayReactor();
		reactor.run();
	}
}

package underwater.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import underwater.react.GatewayReactor;

@SpringBootApplication
public class NeverStopGateway {
	//
	public static void main(String[] args) {
		//
		SpringApplication.run(NeverStopGateway.class, args);
		GatewayReactor reactor = new GatewayReactor();
		reactor.run();
	}
}

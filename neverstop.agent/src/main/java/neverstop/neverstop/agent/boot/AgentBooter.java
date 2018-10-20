package neverstop.neverstop.agent.boot;

import neverstop.neverstop.agent.domain.NeverStopAgent;

public class AgentBooter {
	//
	public static void main(String[] args) throws InterruptedException {
		//
		NeverStopAgent agent = new NeverStopAgent();
		agent.start(2000);
	}
}

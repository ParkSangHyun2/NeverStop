package underwater.agent.converter;

import underwater.util.ClientMessage;

public class DataConverter {
	//
	public static ClientMessage convertToMessageFrom(byte[] datas) {
		//
		String power;
		String reportInterval;
		String bettaryState;
		String cpuState;
		String memoryState;

		switch (datas[1]) {
		case 0:
			power = "LOW";
			break;
		case 1:
			power = "HALF";
			break;
		case 2:
			power = "FULL";
			break;
		default:
			power = "ERROR";
		}

		switch (datas[2]) {
		case 0:
			reportInterval = "1HOUR";
			break;
		case 1:
			reportInterval = "12HOUR";
			break;
		case 2:
			reportInterval = "24HOUR";
			break;
		default:
			reportInterval = "ERROR";
		}
		
		switch (datas[3]) {
		case 0:
			bettaryState = "LOW";
			break;
		case 1:
			bettaryState = "HALF";
			break;
		case 2:
			bettaryState = "FULL";
			break;
		default:
			bettaryState = "ERROR";
		}
		
		switch (datas[4]) {
		case 0:
			cpuState = "LOW";
			break;
		case 1:
			cpuState = "HALF";
			break;
		case 2:
			cpuState = "FULL";
			break;
		default:
			cpuState = "ERROR";
		}
		
		switch (datas[5]) {
		case 0:
			memoryState = "LOW";
			break;
		case 1:
			memoryState = "HALF";
			break;
		case 2:
			memoryState = "FULL";
			break;
		default:
			memoryState = "ERROR";
		}

		return new ClientMessage(String.valueOf(datas[0]), power, reportInterval, bettaryState, cpuState, memoryState);
	}
}
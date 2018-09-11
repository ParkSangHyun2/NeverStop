package kr.co.nextree.util;

import java.io.IOException;

public class DeviceIOException extends RuntimeException{
	//
	private static final long serialVersionUID = 1457900373488196665L;
	
	public DeviceIOException(IOException message) {
		super(message); 
	}
}

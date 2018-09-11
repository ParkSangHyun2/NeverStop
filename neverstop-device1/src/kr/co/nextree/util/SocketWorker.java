package kr.co.nextree.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWorker {
	//  
	private static int MAX_READ_WRITE_LENGTH = 10; 

	private InputStream inputStream;
	private OutputStream outputStream;
	
	private Socket socket;
	
	public SocketWorker(Socket socket) {
		//
		this.socket = socket;		
		try {
			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeSocket() {
		//
		try {
			if (!this.socket.isClosed()) {
				this.socket.close();
			}
		} catch (IOException e) {
			throw new ReactFailException("Fail to close the socket --> " + e.getMessage());
		}
	}
	
	public boolean isAlive() {
		//
		return !socket.isClosed();
	}
	
	public byte[] getReqeusterIp() {
		//
		return socket.getInetAddress().getAddress();
	}
	
	public byte[] read() throws IOException {
		//
		byte[] readBuffer = new byte[10];
		
		inputStream.read(readBuffer, 0, 10);
		return readBuffer;
	}
	
	public void write(byte[] buffer) throws IOException {
		//
		if (outputStream == null || buffer == null) {
			throw new ReactFailException("buffer is null.");
		}

		if (buffer.length > MAX_READ_WRITE_LENGTH) {
			throw new ReactFailException("Can't write more than 10Byte -> " + buffer.length); 
		}

		outputStream.write(buffer);
	}
}

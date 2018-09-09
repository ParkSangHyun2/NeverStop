package underwater.device.reporter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import underwater.util.DispatchFailException;
import underwater.util.SocketWorker;

public class ReportDispatcher {
	private static final int TIME_OUT_IN_SECONDS = 3; 
	
	private SocketWorker socketWorker;
	
	private ReportDispatcher(String serverIp, int listeningPort) {
		//
		this.socketWorker = createSocketWorker(serverIp, listeningPort);
	}

	public static ReportDispatcher getInstance(String ip, int port) {
		return (new ReportDispatcher(ip, port)); 
	}

	public void close() {
		this.socketWorker.closeSocket(); 
	}
	
	public byte[] dispatchReturn(byte[] data) throws IOException {
		//
		socketWorker.write(data);
		byte[] responseData = socketWorker.read();
		return responseData; 
	}

	public void dispatchVoid(byte[] data) throws IOException {
		//
		socketWorker.write(data);
	}

	private  SocketWorker createSocketWorker(String serverIp, int listeningPort) {
		//
		Socket socket = this.prepareSocket(serverIp, listeningPort);
		return (new SocketWorker(socket));
	}

	private Socket prepareSocket(String serverIp, int listeningPort) {
		//
		Socket socket = null; 
		try {
			socket = new Socket();	
			socket.setSoLinger(true, 0);
			socket.setReuseAddress(true);
			socket.connect(new InetSocketAddress(serverIp, listeningPort), TIME_OUT_IN_SECONDS*1000);
			socket.setSoTimeout(3000);
		} catch (UnknownHostException e) {
			throw new DispatchFailException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new DispatchFailException(e.getMessage());
		} 
		
		return socket; 
	}

}

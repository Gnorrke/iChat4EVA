package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPConnection {

	private Socket socket;
	private BufferedReader inputStream;
	private BufferedWriter outputStream;

	public TCPConnection(String serverAddress, int serverPort)
			throws UnknownHostException, IOException {
		socket = new Socket(serverAddress, serverPort);
		initializeStreams();
	}

	public TCPConnection(Socket socket) throws IOException {
		this.socket = socket;
		initializeStreams();
	}

	public void sendLine(String s) throws IOException {
		outputStream.write(s);
		outputStream.newLine();
		outputStream.flush();
	}

	public String receiveLine() throws IOException {
		return inputStream.readLine();
	}

	public void close() throws IOException {
		socket.close();
	}

	private void initializeStreams() throws IOException {
		outputStream = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		inputStream = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
	}

	public InetAddress getInetAddress() {

		return socket.getInetAddress();
	}

}

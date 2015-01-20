package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class TCPConnection {

	private Socket socket;
	private BufferedReader inputStream;
	private BufferedWriter outputStream;

	public TCPConnection(String serverAddress, int serverPort)
			throws UnknownHostException, IOException {
		socket = new Socket(serverAddress, serverPort);
		socket.setSoTimeout(1000 * 60/*secs*/);
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
		String tmp = "";
		try {
			tmp = inputStream.readLine();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Der Server hat die Verbindung abgebrochen oder wurde beendet!",
					"Fehler!", JOptionPane.WARNING_MESSAGE);
			System.exit(-1);
		}
		return tmp;
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

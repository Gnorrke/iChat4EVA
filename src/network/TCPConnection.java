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

/**
 * Die Klasse TCPConnection stellt die zentrale Klasse für die Kommunikation für
 * den Server und den Clienten dar. Mit Hilfe von TCP werden Nachrichten über Sockets übertragen.
 * 
 * @see DemoClient
 * @see Server
 * @author Max
 *
 */
public class TCPConnection {

	private Socket socket;
	private BufferedReader inputStream;
	private BufferedWriter outputStream;

	/**
	 * Der Konstruktor legt mit Hilfe der serverAddress und des serverPorts ein neues Socket an und legt dein Timeout fest
	 * @param serverAddress - Adresse des Kommunikationspartners
	 * @param serverPort - Port auf dem der Kommunikationspartner wartet
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public TCPConnection(String serverAddress, int serverPort)
			throws UnknownHostException, IOException {
		socket = new Socket(serverAddress, serverPort);
		socket.setSoTimeout(60000 * 10/*mins*/);
		initializeStreams();
	}

	public TCPConnection(Socket socket) throws IOException {
		this.socket = socket;
		initializeStreams();
	}

	/**
	 * Die Methode sendLine(String s) sendet einen String über den Output-Stream an den Kommunikationspartner
	 */
	public void sendLine(String s) throws IOException {
		outputStream.write(s);
		outputStream.newLine();
		outputStream.flush();
	}

	/**
	 * Die Methode receiveLine() empfängt einen String über den Input-Stream an den Kommunikationspartner
	 */
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

	/**
	 * Schließt die Verbindung
	 * @throws IOException
	 */
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

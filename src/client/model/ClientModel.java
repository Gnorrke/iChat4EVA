package client.model;

import java.io.IOException;

import javax.swing.JOptionPane;

import network.TCPConnection;

public class ClientModel {

	private static TCPConnection connection = null;
	private String letzteNachricht;
	private String result;
	@SuppressWarnings("unused")
	private String[] userList;
	private String id;

	public ClientModel() {

		this.letzteNachricht = "";
		this.result = "";
		this.userList = new String[100];
		this.id = "";

		connect();
	}

	public void connect() {

		try {
			System.out.println("Verbindungsaufbau ...");

			connection = new TCPConnection("127.0.0.1", 8888);

			// Handshake
			connection.sendLine("%GETID%");
			id = connection.receiveLine();

			receiveUserList();
			System.out.println("Die Verbindung wurde erfolgreich aufgebaut!");

		} catch (Exception e) {
			System.out.println(e + " - Keine Verbindung zum Server möglich");
			System.exit(-1);
		}
	}

	public void disconnect() {

		try {
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}
	}

	public void send(String msg) throws IOException {

		try {
			connection.sendLine(id + msg);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Der Server hat die Verbindung abgebrochen oder wurde beendet!",
							"Fehler!", JOptionPane.WARNING_MESSAGE);
			System.exit(-1);
			e.printStackTrace();
		}
	}

	public String receive() throws IOException {

		result = connection.receiveLine().substring(20);
		// System.out.println(result);
		return result;
	}

	public void receiveUserList() throws IOException {

		connection.sendLine("%GETUSERLIST%");
		result = connection.receiveLine();

		for (int i = 0; i < (result.length() / 20) - 1; i++) {

			System.out.println(result.substring(i * 20, (i + 1) * 20));
		}
	}

	public String getLetzteNachricht() {
		return letzteNachricht;
	}

	public void setID(String id) {
		this.id = id;
	}
}

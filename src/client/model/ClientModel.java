package client.model;

import java.io.IOException;

import network.TCPConnection;

public class ClientModel {

	private static TCPConnection connection = null;
	private String letzteNachricht;
	private String result;
	private String id;

	public ClientModel() {

		this.letzteNachricht = "";
		this.result = "";
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

		connection.sendLine(id + msg);
	}

	public String receive() throws IOException {

		result = connection.receiveLine().substring(20);
		System.out.println(result);
		return result;
	}

	public String getLetzteNachricht() {
		return letzteNachricht;
	}

}

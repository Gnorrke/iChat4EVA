package client.model;

import java.io.IOException;

import javax.swing.JOptionPane;

import network.TCPConnection;

public class ClientModel {

	private static TCPConnection connection = null;
	private String letzteNachricht;
	private String result;
	private String[] userList;
	private static String id;

	public ClientModel() {

		this.letzteNachricht = "";
		this.result = "";
		this.userList = new String[100];
		ClientModel.setId("");

		connect();
	}

	public void connect() {

		try {
			System.out.println("Verbindungsaufbau ...");

			connection = new TCPConnection("127.0.0.1", 8888);

			// Handshake
			connection.sendLine("00000000000000000000%GID%");
			setId(connection.receiveLine().substring(25));

			connection.sendLine(getId() + "%GUL%");
			result = connection.receiveLine();
			this.listUsers(result.substring(25));
			
			System.out.println("Die Verbindung wurde erfolgreich aufgebaut!");

		} catch (Exception e) {
			System.out.println(e + " - Keine Verbindung zum Server möglich");
			System.exit(-1);
		}
	}

	public static void disconnect() {

		try {
			connection.sendLine(getId() + "%DSC%");
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}
	}

	public void send(String msg) throws IOException {

		try {
			connection.sendLine(msg);
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

		result = connection.receiveLine();
		if (result != null && result.contains("%GUL%")) {
			listUsers(result.substring(25));
			return "System: Userliste aktualisiert";
		}
		
		else if(result != null && result.contains("%MSG%")) {
			return result.substring(25);
		}
		
		return result;
	}

	public String[] listUsers(String msg) throws IOException {

		try {
			userList = new String[(msg.length() / 20)];

			for (int i = 0; i < (msg.length() / 20); i++) {
				userList[i] = msg.substring(i * 20, (i + 1) * 20);
				System.out.println(userList[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userList;
	}

	public String getLetzteNachricht() {
		return letzteNachricht;
	}

	public void setID(String id) {
		ClientModel.setId(id);
	}
	
	public String getID() {
		return getId();
	}
	
	public String[] getUserList() {
		return userList;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		ClientModel.id = id;
	}
}

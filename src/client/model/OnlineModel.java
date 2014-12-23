package client.model;



import java.util.ArrayList;
import java.util.List;






import server.User;
import network.TCPConnection;

public class OnlineModel {

	private static TCPConnection connection = null;
	private String id;
	
	static List<User> user_list = new ArrayList<User>();
	

	public OnlineModel() {

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
	
	public TCPConnection getConnection(){
		
		return connection;
		
	}
	public String getID(){
		
		return id;
		
	}

	
	
	
}

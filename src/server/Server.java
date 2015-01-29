package server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import network.TCPConnection;

/**
 * Die Klasse Server stellt die Realisierung des Server dar. Dieser startet ein ServerSocket und
 * lauscht auf dem Port 8888 auf Anfragen. Bei Anfragen startet der Server einen ServerThread für jeden Clienten.
 * Zudem orgranisiert der Server die Clienten in einer syncronizedList.
 * 
 * @see ServerThread
 * @author Max
 *
 */
public class Server {

	static List<User> user_list = Collections
			.synchronizedList(new ArrayList<User>());

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		int serverPort = 8888;

		try {

			serverSocket = new ServerSocket(serverPort);

		} catch (Exception e) {
			System.out.println("Das Serversocket konnte nicht erzeugt werden");
			return;
		}

		while (true) {
			try {

				System.out.println("Warten auf Verbindungsaufbau...");

				/**
				 * Auf Anfrage von einem Client warten, danach wird ein ServerThread gestartet,
				 * welcher den Handshake und alles andere übernimmt
				 *  
				 * @see ServerThread
				 * @see TCPConnection
				 */
				TCPConnection tcpConnection = new TCPConnection(
						serverSocket.accept());

				// Client ID vergeben und in User-Liste eintragen
				String ID = createUniqueID();
				User newUser = new User(tcpConnection, 8888, ID);
				addUser(tcpConnection, 8888, ID);

				showUserlist();
				new ServerThread(newUser);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Erstellt eine 20-stellige UUID, die jeden Clienten zugewiesen wird
	 * @return
	 */
	private static String createUniqueID() {
		return UUID.randomUUID().toString().substring(0, 20);
	}

	/**
	 * Gibt in der Konsole des Servers die verbundenen User aus
	 */
	public static void showUserlist() {
		System.out.println("Userliste: \n");

		int i = 0;
		for (User user : user_list) {
			i++;
			System.out.println("User " + i + " ID: " + user.getID() + " IP: "
					+ user.getAddress() + " Port: " + user.getPort());
		}
		System.out.println();
	}

	/**
	 * Die Methode addUser(TCPConnection address, int port, String id) erstellt einen User für jeden Clienten
	 * und fügt diese der user_list hinzu
	 * @param address - IP-Adresse des Clienten
	 * @param port - Port des Clienten
	 * @param id - uniqueID des Clienten 
	 */
	public static void addUser(TCPConnection address, int port, String id) {
		user_list.add(new User(address, port, id));
	}

	/**
	 * Die Methode removeUser(String id) löscht den Clienten mit der id aus der user_list.
	 * Da mehrere Threads gleichzeitig darauf zugreifen müssen wird eine synchronizedList verwendet
	 * @param id - uniqueID der Clienten
	 */
	public static void removeUser(String id) {
		System.out.println(id + " wurde abgemeldet!");
		
		for (User user : user_list) {
			if (user.getID().equals(id)) {
				user_list.remove(user);
				return;
			}
		}
	}

	/**
	 * Getter für die user_list
	 * @return 
	 */
	public static synchronized List<User> getUserList() {
		return user_list;
	}
}

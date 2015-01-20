package server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import network.TCPConnection;

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

				/*
				 * Auf Anfrage von einem Client warten
				 * 
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

	private static String createUniqueID() {
		return UUID.randomUUID().toString().substring(0, 20);
	}

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

	public static void addUser(TCPConnection address, int port, String id) {
		user_list.add(new User(address, port, id));
	}

	public static void removeUser(String id) {
		System.out.println(id + " wurde abgemeldet!");
		
		for (User user : user_list) {
			if (user.getID().equals(id)) {
				user_list.remove(user);
				return;
			}
		}
	}

	public static synchronized List<User> getUserList() {
		return user_list;
	}
}

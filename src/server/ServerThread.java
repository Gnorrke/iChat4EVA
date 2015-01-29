package server;

import java.io.IOException;

import network.TCPConnection;

/**
 * Die Klasse ServerThread dient als Kommunikationsschnittstelle für jeden einzelnen Clienten.
 * Jedem Clienten wird eindeutig einem User zugewiesen und kommuniziert über TCP mit dem Server
 * @author Max
 *
 */
public class ServerThread extends Thread {

	private User user;
	private TCPConnection connection;
	private String uniqueID;

	public ServerThread(User user) {
		this.user = user;
		this.connection = user.getTCPConnection();
		this.uniqueID = user.getID();
		this.start();
	}

	/**
	 * Startet dem Server
	 */
	public void run() {
		System.out.println("Verbindung mit " + uniqueID
				+ " erfolgreich aufgebaut!");
		String request = "";

		while (true) {
			try {
				request = connection.receiveLine();

				/**
				 * Trennt die Verbindung zum Clienten mit dem Flag %DSC%
				 */
				if (request.contains("%DSC%")) {
					System.out.println(uniqueID + " wird abgemeldet...");
					break;
				}

				/**
				 * Sendet dem Clienten die uniqueID
				 */
				else if (request.contains("%GID%")) {
					System.out.println(uniqueID + " wird abgemeldet...");
					connection.sendLine(uniqueID + "%GID%" + uniqueID);
				}

				/**
				 * Erstellt ein String aus allen uniqueIds der verbundenen Clienten und sendet diesen String an den Client
				 */
				else if (request.contains("%GUL%")) {
					System.out.println("Userliste wurde an " + uniqueID + " gesendet");
					StringBuilder tmp = new StringBuilder();
					tmp.append(uniqueID + "%GUL%");
					
					for (User user : Server.getUserList()) {
						tmp.append(user.getID());
					}
					
					connection.sendLine(tmp.toString());
				}
				
				/**
				 * Parst die Nachricht und sendet an den Empfänger die Nachricht
				 */
				else if (request.substring(0, 25).contains("%MSG%")) {

					for (User user : Server.getUserList()) {
						if (user.getID().equals(request.substring(25, 45))) user.getTCPConnection().sendLine(request);
						System.out.println("Eine Nachricht wurde an " + request.substring(25, 45) + "gesendet");
					}
				}
				
				
			} catch (Exception e) {
				//Trennt die Verbindung zum Clienten und entfernt diesen aus der user_list
				System.out.println("Der Client " + uniqueID
						+ " hat die Verbindung geschlossen");
				try {
					connection.close();

				} catch (IOException ex) {
					System.out.println(ex);
				}
				Server.removeUser(user.getID());

				break;
			}
		}
		Server.removeUser(user.getID());
	}
}

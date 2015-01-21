package server;

import java.io.IOException;

import network.TCPConnection;

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

	public void run() {
		System.out.println("Verbindung mit " + uniqueID
				+ " erfolgreich aufgebaut!");
		String request = "";

		while (true) {
			try {
				request = connection.receiveLine();

				if (request.contains("%DSC%")) {
					System.out.println(uniqueID + " wird abgemeldet...");
					break;
				}

				else if (request.contains("%GID%")) {
					System.out.println(uniqueID + " wird abgemeldet...");
					connection.sendLine(uniqueID + "%GID%" + uniqueID);
				}

				else if (request.contains("%GUL%")) {
					System.out.println("Userliste wurde an " + uniqueID + " gesendet");
					StringBuilder tmp = new StringBuilder();
					tmp.append(uniqueID + "%GUL%");
					
					for (User user : Server.getUserList()) {
						tmp.append(user.getID());
					}
					
					connection.sendLine(tmp.toString());
				}
				
				else if (request.substring(0, 25).contains("%MSG%")) {

					for (User user : Server.getUserList()) {
						if (user.getID().equals(request.substring(25, 45))) user.getTCPConnection().sendLine(request);
						System.out.println("Eine Nachricht wurde an " + request.substring(25, 45) + "gesendet");
					}
				}
				
				
			} catch (Exception e) {
				
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

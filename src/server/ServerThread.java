package server;

import java.io.IOException;

import network.TCPConnection;

public class ServerThread extends Thread{

	
	
	private TCPConnection connection;
	private String uniqueID;
	
	public ServerThread(TCPConnection tcpSocket, String ID)
	{
		this.connection = tcpSocket;
		this.uniqueID = ID;
		this.start();
	}
	
	public void run()
	{
		System.out.println("Verbindung mit " + uniqueID + " erfolgreich aufgebaut!");
		String request = "";

		
		while (true) {
			try {

				request = connection.receiveLine();

				if (request.equals("%GETID%")) {
					connection.sendLine(uniqueID);
				}

				else if (request != null) {
					System.out.println(request.substring(20));
					connection.sendLine(uniqueID + request.substring(20));
				}

			} catch (Exception e) {
				System.out.println("Der Client " + uniqueID + " hat die Verbindung geschlossen");
				try {
					connection.close();
					
				} catch (IOException ex) {
					System.out.println(ex);
				}
				
				break;
			}
		}
		
		Server.removeUser(uniqueID);
	}
}

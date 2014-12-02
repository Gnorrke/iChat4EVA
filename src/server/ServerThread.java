package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
		
		do {
			try {

				request = connection.receiveLine();

				if (request != null) {
					System.out.println(request);
				}

				connection.sendLine(uniqueID + request);

			} catch (Exception e) {
				System.out.println(e);
			}
		} while (!request.equals("Disconnect"));

		System.out.println("Verbindung wird geschlossen");
		
		try {
			
			connection.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

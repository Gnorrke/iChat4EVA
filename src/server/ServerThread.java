package server;

import java.io.IOException;

import network.TCPConnection;

public class ServerThread extends Thread{

	private TCPConnection connection;
	
	public ServerThread(TCPConnection tcpSocket)
	{
		this.connection = tcpSocket;
		this.start();
	}
	
	public void run()
	{
		String request = "";
		
		do {
			try {

				request = connection.receiveLine();

				if (request != null) {
					System.out.println(request);
				}

				connection.sendLine(request);

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

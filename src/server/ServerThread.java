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
				
				else if(request.substring(20, 24).equals("%ID%")){
				
					for(User user : Server.user_list){
						System.out.println("test1 "+request.substring(24, 44));
						if(request.substring(24, 44).equals(user.getID())){
							System.out.println("test2 "+request.substring(24, 44));
							user.getConnection().sendLine(uniqueID + request.substring(20));
						}
					}
					
				}
				else if (request != null) {
					System.out.println(request.substring(20));
					System.out.println("test1 ----- "+request.substring(21, 24));
					//Nachricht an alle Clients schicken
					for(User user : Server.user_list){
						
						user.getConnection().sendLine(uniqueID + request.substring(20));
						
					}
					
					
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

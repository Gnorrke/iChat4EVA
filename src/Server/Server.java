package Server;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import Socket.UDPSocket;

public class Server{
	
	public static void main(String[] args) throws IOException {
		
	
		ArrayList<user> users = new ArrayList();
		
		UDPSocket udpSocket = null;
		try {
			
			//UDP Socket erstellen und Port festlegen
			udpSocket = new UDPSocket(1250);
			
			
			System.out.println("Auf Anfragen warten...");
			
			while(true)
			{
				// receive request
				String request = udpSocket.receive(20);
				
				
				if(request.contains("%ID%")){
					
					user tempuser = new user("127.0.0.1",1250, request.substring( 4 ));
				
					users.add(tempuser);
					System.out.println("Erfolgreich angemeldet");
					System.out.println(users.toString());
				
								
			
				}
				else{
					
						// generate answer
						String answer = "Nachricht erhalten";
						
						// send answer
						udpSocket.reply(answer);
					
					}
			}
			
		} catch (SocketException e) {
			System.out.println(e);
			System.out.println("=> closing datagram socket");
			//Beenden
			if(udpSocket != null)
			{
				udpSocket.close();
			}
		}
		
		
	}
	
}
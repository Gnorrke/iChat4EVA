package Server;

import java.awt.image.ReplicateScaleFilter;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.UUID;

import Socket.UDPSocket;

public class Server{
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<User> users = new ArrayList<User>();
		
		UDPSocket udpSocket = null;
		try {
			
			//UDP Socket erstellen und Port festlegen
			udpSocket = new UDPSocket(1250);
			
			
			System.out.println("Auf Anfragen warten...");
			
			while(true)
			{
				// receive request
				String request = udpSocket.receive(20);
				
				
				if(request.contains("%Hello Server%")) {
					
					String tempID = replyUniqueID();
					
					User tempuser = new User("127.0.0.1",1250, tempID);
					users.add(tempuser);
					
					udpSocket.reply(tempID);
					System.out.println("Erfolgreich angemeldet");
					System.out.println(users.toString());
				}
				else {
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
	
	private static String replyUniqueID ()
	{
		return UUID.randomUUID().toString();
	}
	
}
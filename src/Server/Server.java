package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.UUID;

import Socket.UDPSocket;

public class Server{
	
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<User> users = new ArrayList<User>();
		String request;
		
		UDPSocket udpSocket = null;
		try {
			
			//UDP Socket erstellen und Port festlegen
			udpSocket = new UDPSocket(1250);
			
			
			System.out.println("Auf Anfragen warten...");
			
			while(true)
			{
				// receive request
				request = "";
				request = udpSocket.receive(200);		//change this value for longer messages
				
				if(request.contains("%Hello Server%")) {
					
					String tempID = replyUniqueID();
					
					InetAddress addr = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
					
					User tempuser = new User(addr, 1250, tempID);
					users.add(tempuser);
					
					udpSocket.reply(tempID);
					System.out.println("Erfolgreich angemeldet");
					System.out.println(users.toString());
				}
				else {
					// generate answer
					String answer = "Nachricht erhalten";
						
					// print answer
					for (User user : users) {
						if(udpSocket.getSenderAddress().equals(user.getAddress())) System.out.println("ID: " + user.getID().substring(0, 4) + "     - Nachricht: " +  request);
					}

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
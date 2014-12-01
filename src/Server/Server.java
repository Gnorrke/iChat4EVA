package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Socket.UDPSocket;

public class Server{
	
	static ArrayList<User> users = new ArrayList<User>();

	public static void main(String[] args) throws IOException {
		

		//Map<String, InetAddress> userList = new HashMap<String, InetAddress>();
		
		String request, newID, tempID, tempMessage, userlist = "%Userlist%";
		
		UDPSocket udpSocket = null;
		try {
			
			//UDP Socket erstellen und Port festlegen
			udpSocket = new UDPSocket(1250);
			
			
			System.out.println("Auf Anfragen warten...");
			
			while(true)
			{
				// receive request
				request = udpSocket.receive(200);		//change this value for longer messages

				if(request.contains("%Hello Server%")) {
					
					newID = replyUniqueID();
					
					InetAddress addr = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
					
					User tempuser = new User(addr, 1250, newID);
					users.add(tempuser);
					
					udpSocket.reply(newID);
					System.out.println("Erfolgreich angemeldet");
					System.out.println(users.toString());
				}
				
				else if (request.contains("%Userlist%")) {
					for (User user : users) {
						userlist += user.getID();
					}
					udpSocket.reply(userlist);
					userlist = "%Userlist%";
					System.out.println("Userliste gesendet");
				}
				
				else if (request.contains("%Disconnect%")) {
					tempID = request.substring(0, 19);
					
					for (User user : users) {
						if(tempID.equals(user.getID())) {
							disconnect(user);
							break;
						}
					}
				}
				
				else {
					// generate answer
					String answer = "Nachricht erhalten";
						
					// print answer
					tempID = request.substring(0, 19);
					tempMessage = request.substring(19);
					for (User user : users) {
						if(tempID.equals(user.getID())) System.out.println("ID: " + tempID + "     - Nachricht: " +  tempMessage);
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
		return UUID.randomUUID().toString().substring(0, 19);
	}
	
	private static void disconnect(User user)
	{
		System.out.println("User " + user.getID() +  " abgemeldet");
		users.remove(user);
	}
}
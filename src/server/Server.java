package server;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.UUID;

import network.TCPConnection;

public class Server{
	
	static ArrayList<User> user_list = new ArrayList<User>();

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		int serverPort = 8888;
		
		try {
			
			serverSocket = new ServerSocket(serverPort);
			
		} catch (Exception e) {
			System.out.println("Das Serversocket konnte nicht erzeugt werden");
			return;
		}
		
		while (true) {
			try {
				
				System.out.println("Warten auf Verbindungsaufbau...");
				
			
				/*Auf Anfrage von einem Client warten
				@see TCPConnection*/
				TCPConnection tcpConnection = new TCPConnection(serverSocket.accept());
				
				//Client ID vergeben und  in User-Liste eintragen
				String ID = createUniqueID();
				User temp_user = new User(tcpConnection.getInetAddress(),8888,ID);
				user_list.add(temp_user);
				showUser();
				new ServerThread(tcpConnection, ID );
				
				
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private static String createUniqueID ()
	{
		return UUID.randomUUID().toString().substring(0, 19);
	}
	
	public static void showUser(){
		int i = 0;
		for (User user : user_list) {
			i++;
			System.out.println("User "+i+" ID: "+user.getID()+" IP: "+ user.getAddress()+" Port: "+user.getPort());
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}




















//	
//
//	public static void main(String[] args) throws IOException {
//		
//
//		//Map<String, InetAddress> userList = new HashMap<String, InetAddress>();
//		
//		String request, newID, tempID, tempMessage, userlist = "%Userlist%";
//		
//		UDPSocket udpSocket = null;
//		try {
//			
//			//UDP Socket erstellen und Port festlegen
//			udpSocket = new UDPSocket(1250);
//			
//			
//			System.out.println("Auf Anfragen warten...");
//			
//			while(true)
//			{
//				// receive request
//				request = udpSocket.receive(200);		//change this value for longer messages
//
//				if(request.contains("%Hello Server%")) {
//					
//					newID = replyUniqueID();
//					
//					InetAddress addr = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
//					
//					User tempuser = new User(addr, 1250, newID);
//					users.add(tempuser);
//					
//					udpSocket.reply(newID);
//					System.out.println("Erfolgreich angemeldet");
//					System.out.println(users.toString());
//				}
//				
//				else if (request.contains("%Userlist%")) {
//					for (User user : users) {
//						userlist += user.getID();
//					}
//					udpSocket.reply(userlist);
//					userlist = "%Userlist%";
//					System.out.println("Userliste gesendet");
//				}
//				
//				else if (request.contains("%Disconnect%")) {
//					tempID = request.substring(0, 19);
//					
//					for (User user : users) {
//						if(tempID.equals(user.getID())) {
//							disconnect(user);
//							break;
//						}
//					}
//				}
//				
//				else {
//					// generate answer
//					String answer = "Nachricht erhalten";
//						
//					// print answer
//					tempID = request.substring(0, 19);
//					tempMessage = request.substring(19);
//					for (User user : users) {
//						if(tempID.equals(user.getID())) System.out.println("ID: " + tempID + "     - Nachricht: " +  tempMessage);
//					}
//
//					// send answer
//					udpSocket.reply(answer);
//				}
//			}
//			
//		} catch (SocketException e) {
//			System.out.println(e);
//			System.out.println("=> closing datagram socket");
//			//Beenden
//			if(udpSocket != null)
//			{
//				udpSocket.close();
//			}
//		}
//	}
//	
//	private static String replyUniqueID ()
//	{
//		return UUID.randomUUID().toString().substring(0, 19);
//	}
//	
//	private static void disconnect(User user)
//	{
//		System.out.println("User " + user.getID() +  " abgemeldet");
//		users.remove(user);
//	}
//}
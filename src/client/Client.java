package client;

import java.util.Scanner;

import network.TCPConnection;

public class Client{
	
	public static void main(String[] args) {

		TCPConnection connection = null;
		Scanner sc = new Scanner(System.in);
		String eingabe = "", result;
		
		try {
			System.out.println("Verbindungsaufbau ...");
			
			connection = new TCPConnection("127.0.0.1", 8888);
			
			System.out.println("Die Verbindung wurde erfolgreich aufgebaut!");
			
			do {
				eingabe = sc.nextLine();
				
				connection.sendLine(eingabe);
				result = connection.receiveLine();
				
				System.out.println(result);
				
			} while (!eingabe.equals("Disconnect"));
			
		} catch (Exception e) {
			System.out.println(e + " - Keine Verbindung zum Server möglich");
		}
		
		if (connection != null) {
			System.out.println("Verbindung wird geschlossen!");
			
			try {
				
				connection.close();
				sc.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	
	
	
}
//
//	private static final int TIMEOUT = 10000; // 10 seconds
//	private static String id;
//	private static UDPSocket udpSocket = null;
//	private static Scanner sc;
//	private static InetAddress serverAddr;
//
//	public static void main(String[] args) throws IOException {
//
//		sc = new Scanner(System.in);
//
//		try {
//			String eingabe = null;
//
//			// create datagram socket
//			udpSocket = new UDPSocket();
//			udpSocket.setTimeout(TIMEOUT);
//
//			serverAddr = InetAddress.getByName("127.0.0.1");
//
////			System.out.println("ID eingeben");
////			eingabe = sc.next();
//			udpSocket.send("%Hello Server%", serverAddr, 1250);
//			id = udpSocket.receive(20).toString();
//			System.out.println(id);
//
//			while(true){
//
//				System.out.println("Nachricht eingeben");
//				eingabe = sc.nextLine();
//
//				udpSocket.send(id + eingabe, serverAddr, 1250);
//				String reply = null;
//
//				if (eingabe.contains("%Disconnect%")) close();
//				
//				// receive reply
//				try
//				{
//					reply = udpSocket.receive(300);
//					
//					if (reply.contains("%Userlist%")) {
//						String temp = reply.substring(10);
//						
//						for (int i = 0, j = temp.length() / 20 ; i <= j ; i++) {
//							System.out.println(temp.substring(0, 19));
//							temp = temp.substring(19);
//						}
//						temp = "";
//					}
//					
//					else System.out.println(reply);
//				}
//				catch(Exception e) {
//					System.out.println(e);
//				}
//			}
//		}
//		
//		catch (Exception e) {
//			System.out.println(e);
//			System.out.println("=> DatagramSocket wird geschlossen");
//		}
//
//		close();
//	}
//	
//	public static void close() throws IOException {
//		
//		udpSocket.send(id + "%Disconnect%", serverAddr, 1250);
//		if(udpSocket != null)
//		{
//			udpSocket.close();
//		}
//		
//		sc.close();
//	}
//}
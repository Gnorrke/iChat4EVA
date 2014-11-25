package Client;

import java.net.InetAddress;
import java.util.Scanner;

import Socket.UDPSocket;

public class Client{
	
	private static final int TIMEOUT = 10000; // 10 seconds
	
	public static void main(String[] args) {
		
		UDPSocket udpSocket = null;
		Scanner sc = new Scanner(System.in);
		
			
		try {
			String eingabe = null;
			
			// create datagram socket
			udpSocket = new UDPSocket();
			udpSocket.setTimeout(TIMEOUT);
			

			InetAddress serverAddr = InetAddress.getByName("127.0.0.1");
			
			eingabe = sc.next();
			udpSocket.send(eingabe, serverAddr, 1250);
						
			
			while(true){			
			
			eingabe = sc.next();
			
			udpSocket.send(eingabe, serverAddr, 1250);
			String reply = null;
			
			// receive reply
			try
			{
				reply = udpSocket.receive(20);
				System.out.println(reply);
			
			}
			catch(Exception e)
			{
				System.out.println(e);
			}			
			
			}
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println("=> DatagramSocket wird geschlossen");
		}
		
		if(udpSocket != null)
		{
				udpSocket.close();
		}
		
		sc.close();
		
	}
		
	
}
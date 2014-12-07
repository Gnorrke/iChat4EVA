//package client.model;
//
//import java.awt.GridLayout;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import network.TCPConnection;
//
//public class Client{
//	
//	private static TCPConnection connection = null;
//	private static Scanner sc = new Scanner(System.in);
//	private static String eingabe = "";
//	private static String result;
//	private static String id;
//	
//	public static void main(String[] args) {
//		
//		try {
//			System.out.println("Verbindungsaufbau ...");
//			
//			connection = new TCPConnection("127.0.0.1", 8888);
//			
//			//Handshake
//			connection.sendLine("%GETID%");
//			id = connection.receiveLine();
//			
//			System.out.println("Die Verbindung wurde erfolgreich aufgebaut!");
//			
//			do {
//				eingabe = sc.nextLine();
//				
//				connection.sendLine(id + eingabe);
//				result = connection.receiveLine();
//				
//				System.out.println(result);
//				
//			} while (!eingabe.equals("Disconnect"));
//			
//		} catch (Exception e) {
//			System.out.println(e + " - Keine Verbindung zum Server möglich");
//		}
//		
//		if (connection != null) {
//			System.out.println("Verbindung wird geschlossen!");
//			
//			try {
//				
//				connection.close();
//				sc.close();
//				
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//		}
//	}
//}

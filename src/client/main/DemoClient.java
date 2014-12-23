package client.main;



import network.TCPConnection;
import client.controller.ClientController;
import client.controller.OnlineController;

public class DemoClient {
	

	static TCPConnection connection = null;
	static String id;

	

	static OnlineController onlinelist;
	
	public static void main(String[] args) {
		
		onlinelist = new OnlineController();
		
		//Thread zum Empfangen und Anzeigen der Nachrichten starten
		OnlineList c1 = new OnlineList(onlinelist);
		
		
	
		
		c1.start();
		
		
		
	}
}
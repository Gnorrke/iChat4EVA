package client.main;

import client.model.ClientModel;
import client.model.ClientThread;

/**
 * Die Klasse DemoClient stellt eine konkrete Realisierung des Clienten dar.
 * Sie setzt einen ShutdownHook, die den Clienten sauber vom Server trennt, falls das Programm per Exit Signal (STRG + c, in der CMD)
 * oder anderweitig unerwartet beendet wird.
 * Zudem startet sie den Thread, der den Clienten startet und auf Nachrichten vom Server wartet
 * @author Max
 *
 */
public class DemoClient {

	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			public void run() {
				ClientModel.disconnect();
			}
		}));
		
		ClientThread client = new ClientThread();
		client.start();
	}
}

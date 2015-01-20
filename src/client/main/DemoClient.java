package client.main;

import client.model.ClientModel;
import client.model.ClientThread;

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

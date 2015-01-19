package client.main;

import client.model.ClientThread;

public class DemoClient {

	public static void main(String[] args) {

		ClientThread client = new ClientThread();
		client.start();
	}
}

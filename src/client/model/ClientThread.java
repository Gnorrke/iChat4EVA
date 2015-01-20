package client.model;

import client.controller.ClientController;

public class ClientThread extends Thread {

	private ClientController controller;

	public ClientThread() {

		controller = new ClientController();
		controller.showView();
	}

	public void run() {

		while (true) {

			try {
				sleep(1);
				controller.receiveMessage();
			} catch (InterruptedException e) {
				System.exit(-1);
				e.printStackTrace();
			}
		}
	}

}
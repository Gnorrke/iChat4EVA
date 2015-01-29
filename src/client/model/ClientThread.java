package client.model;

import client.controller.ClientController;

/**
 * Die ClientThread Klasse realisiert einen Thread, der jede Sekunde überprüft, ob der Server
 * eine Nachricht gesendet hat. 
 * @author Max
 *
 */
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

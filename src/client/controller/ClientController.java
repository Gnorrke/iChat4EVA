package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import client.model.ClientModel;
import client.view.ClientView;

public class ClientController {

	private ClientView view;
	private ClientModel model;

	public ClientController() {
		this.model = new ClientModel();
		this.view = new ClientView();

		addListener();
	}

	public void showView() {
		this.view.setVisible(true);
	}

	/*
	 * Die Listener, die wir aus den internen Klassen generieren werden der View
	 * bekannt gemacht, sodass mit dem Controller kommuniziert werden kann
	 */

	private void addListener() {
		this.view.setSendMessageListener(new SendMessageListener());

	}

	class SendMessageListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				model.send(view.getEingabe());

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public synchronized void receiveMesseage() {
		try {
			// System.out.println("RecevieMesseage()");
			view.addMessage(model.receive());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

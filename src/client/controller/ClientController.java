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
		this.view.addUserListe(model.getUserList());
	}

	/*
	 * Die Listener, die wir aus den internen Klassen generieren werden der View
	 * bekannt gemacht, sodass mit dem Controller kommuniziert werden kann
	 */

	private void addListener() {
		this.view.setSendMessageListener(new SendMessageListener());
		this.view.setUserListListener(new UserListListener());

	}

	class SendMessageListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				if (!view.selectionEmpty() && view.getSelected() != null) {
					System.out.println(view.getSelected());
					model.send(model.getID() + "%MSG%" + view.getSelected() + view.getEingabe());
				} else view.addMessage("System: Sie haben keinen Empfänger ausgewählt!");

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	class UserListListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				model.send(model.getID() + "%GUL%");
				Thread.sleep(200);
				view.addUserListe(model.getUserList());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public synchronized void receiveMessage() {
		try {
			view.addMessage(model.receive());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

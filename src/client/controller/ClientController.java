package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import client.model.ClientModel;
import client.view.ClientView;

/**
 * Die Klasse ClientController realisiert den Controller im MVC-Pattern des Clienten.
 * Sie holt sich die Nachrichten und die Userliste vom Model und gibt sie an die View weiter.
 * Außerdem holt der Controller sich die Eingaben der aus der GUI und gibt diese an ClientModel weiter
 *
 * @see ClientModel
 * @see ClientView
 * 
 * @author Max Niederauer, Fabian Kalweit
 */

public class ClientController {

	private ClientView view;
	private ClientModel model;

	/**
	 * Dem Controller wird die Model und die View zugewiesen
	 */
	public ClientController() {
		this.model = new ClientModel();
		this.view = new ClientView();

		addListener();
	}

	/**
	 * Die im Kontrsuktur generierte View wird dargestellt
	 */
	public void showView() {
		this.view.setVisible(true);
		this.view.addUserListe(model.getUserList());
	}

	/**
	 * Die Listener, die wir aus den internen Klassen generieren werden der View
	 * bekannt gemacht, sodass mit dem Controller kommuniziert werden kann
	 */

	private void addListener() {
		this.view.setSendMessageListener(new SendMessageListener());
		this.view.setUserListListener(new UserListListener());

	}

	/**
	 * Listener Klasse für das Senden von Nachrichten aus dem JTextArea
	 * @author Max
	 *
	 */
	class SendMessageListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				if (!view.selectionEmpty() && view.getSelected() != null) {
					System.out.println(view.getSelected());
					model.send(model.getID() + "%MSG%" + view.getSelected()
							+ view.getEingabe());
					view.addOwnMessage(view.getEingabe());
					view.resetView();
				} else
					view.addMessage("System: Sie haben keinen Empfänger ausgewählt!");

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Listener für die JList (Userliste)
	 * @author Max
	 *
	 */
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
			String tmp = model.receive();
			view.addMessage(model.getLastSender() + ": " + tmp);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

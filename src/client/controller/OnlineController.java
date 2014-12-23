package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;






import network.TCPConnection;
import client.main.ClientThread;
import client.model.OnlineModel;
import client.view.OnlineView;

public class OnlineController {

	
	private OnlineView view;
	private OnlineModel model;
	
	public OnlineController() {
		this.model = new OnlineModel();
		
		this.view = new OnlineView();
		
		addListener();
	}

	public void showView() {
		this.view.setVisible(true);
	}
	
	
	
	private void addListener(){
		this.view.setStartChatListener(new StartChatListener());
		
		
	}
	
	class StartChatListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
				ClientController controller;
				
				System.out.println("Test");
				
			
				controller = new ClientController(model.getConnection(),model.getID());
				
				ClientThread c2 = new ClientThread(controller);
				
				c2.start();

				controller.showView();
			
		}
	}
	
	public TCPConnection getConnection(){
		
		return model.getConnection();
		
	}
	
	public String getID(){
		
		return model.getID();
		
	}
	
	
}
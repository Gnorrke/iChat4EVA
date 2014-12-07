package client.main;

import client.controller.ClientController;


public class DemoClient {

	static ClientController controller;
	
	public static void main(String[] args) {
		
		controller = new ClientController();
		controller.showView();
	}
}

package client.main;


import client.controller.ClientController;



public class ClientThread extends Thread {
	
		private ClientController controller;
	
		public  ClientThread(ClientController controller) {
						
			this.controller = controller;
						
		}
	
		public void run() {
					
			
			while(true){ 
			
				
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				controller.RecevieMesseage();
			}
		}
	
	
}

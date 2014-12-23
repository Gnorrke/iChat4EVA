package client.main;



import client.controller.OnlineController;


public class OnlineList extends Thread{

	private OnlineController controller;
							
	
	public OnlineList(OnlineController controller){
		
		
		this.controller = controller;
		
	}			
	
	public void run(){
		
		System.out.println("run  online List");
		controller.showView();
		
	}
	
	
}

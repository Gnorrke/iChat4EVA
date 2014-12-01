package Server;

import java.net.InetAddress;

public class User {
	
	private InetAddress address;
	private int port;
	private String ID;
	
	public User(InetAddress adress,int port,String ID){
		this.address = adress;
		this.port = port;
		this.ID = ID;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getID() {
		return ID;
	}
}

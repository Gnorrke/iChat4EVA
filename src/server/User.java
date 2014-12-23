package server;

import java.net.InetAddress;

import network.TCPConnection;

public class User {
	
	private InetAddress address;
	private int port;
	private String ID;
	private TCPConnection connection;
	
	public User(InetAddress adress,int port,String ID,TCPConnection connection){
		this.address = adress;
		this.port = port;
		this.ID = ID;
		this.connection = connection;
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
	
	public TCPConnection getConnection(){
		
		return connection;
		
	}
	
	public void setConnection(TCPConnection connection){
		
		this.connection = connection;
		
	}
}

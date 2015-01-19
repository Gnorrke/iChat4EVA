package server;

import java.net.InetAddress;

import network.TCPConnection;

public class User {
	
	private InetAddress address;
	private int port;
	private String ID;
	private TCPConnection connection;
	
	public User(TCPConnection connection,int port,String ID){
		this.connection = connection;
		this.address = connection.getInetAddress();
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
	
	public TCPConnection getTCPConnection() {
		return connection;
	}
}

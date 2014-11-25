package Server;


import java.net.InetAddress;

public class user {
	
	private String address;
	private int port;
	private String ID;
	
	
	public user(String adress,int port,String ID){
		
		this.address = adress;
		this.port = port;
		this.ID = ID;
		
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	

}

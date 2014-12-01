package Socket;

import java.io.*;
import java.net.*;
public class UDPSocket
{
	protected DatagramSocket socket;
	private InetAddress address;
	private int port;
	
	protected UDPSocket(DatagramSocket socket)
	{
		this.socket = socket;
	}

	public UDPSocket() throws SocketException
	{
		this(new DatagramSocket());
	}

	public UDPSocket(int port) throws SocketException
	{
		this(new DatagramSocket(port));
	}

	public void send(String s, InetAddress rcvrAddress, int rcvrPort) throws IOException
	{
		byte[] outBuffer = s.getBytes();
		DatagramPacket outPacket = new DatagramPacket(outBuffer,outBuffer.length,rcvrAddress,rcvrPort);
		socket.send(outPacket);
	}


	public String receive(int maxBytes) throws IOException
	{
		byte[] inBuffer = new byte[maxBytes];
		DatagramPacket inPacket = new DatagramPacket(inBuffer,inBuffer.length);
		socket.receive(inPacket);
		address = inPacket.getAddress(); // addr for reply packet
		port = inPacket.getPort(); // port for reply packet
		return new String(inBuffer, 0, inPacket.getLength());
	}

	public void reply(String s) throws IOException
	{
		if(address == null)
		{
			throw new IOException("no one to reply");
		}
		send(s, address, port);
	}
	

	public InetAddress getSenderAddress()
	{
		return address;
	}

	public int getSenderPort()
	{
		return port;
	}

	public void setTimeout(int timeout) throws SocketException
	{
		socket.setSoTimeout(timeout);
	}

	public void close()
	{
		socket.close();
	}

	byte[] toBytes(int i)
	{
	  byte[] result = new byte[4];

	  result[0] = (byte) (i >> 24);
	  result[1] = (byte) (i >> 16);
	  result[2] = (byte) (i >> 8);
	  result[3] = (byte) (i /*>> 0*/);

	  return result;
	}
}

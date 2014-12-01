package test;

public class Server
{
	public static void main(String[] args)
	{
		int counter = 0;
		UDPSocket udpSocket = null;
		try
		{
			// create socket
			udpSocket = new UDPSocket(1250);
			// wait for request packets
			System.out.println("waiting for client requests");
			// execute client requests
			while(true)
			{
				// receive request
				String request = udpSocket.receive(20);
				// perform increment operation
				if(request.equals("increment"))
				{
					// perform increment
					counter++;
				}
				else if(request.equals("reset"))
				{
					// perform reset
					counter = 0;
					System.out.println("counter reset by " + udpSocket.getSenderAddress() + ":" + udpSocket.getSenderPort());
				}
				// generate answer
				String answer = String.valueOf(counter);
				// send answer
				udpSocket.reply(answer);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("=> closing datagram socket");
			if(udpSocket != null)
			{
				udpSocket.close();
			}
		}
	}
}
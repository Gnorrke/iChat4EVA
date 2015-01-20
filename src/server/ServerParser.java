package server;

import java.io.IOException;

public class ServerParser {

	private User user;
	
	public ServerParser(User user) {
		this.user = user;
	}
	
	public String decodeMsg(String msg) throws IOException {
		if (msg.contains("%GID%")) {
			return msg + user.getID();
		} 
		
		else if (msg.contains("%GUL%")) {
			StringBuilder tmp = new StringBuilder();
			tmp.append(user.getID() + "%GUL%");
			
			for (User user : Server.getUserList()) {
				tmp.append(user.getID());
			}
			return tmp.toString();
		}
		
		else if (msg.contains("%MSG%")) {
			return msg;
		}
		
		else return "error01";
	}
}

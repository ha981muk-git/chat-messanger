package edu.fra.uas.chat;

import java.net.SocketException;
import java.net.UnknownHostException;

import edu.fra.uas.net.Client;

public class ChatClient extends Client{

	public ChatClient(String serverAddress, int serverPort, String bindAddress, int port)
			throws SocketException, UnknownHostException {
		super(serverAddress, serverPort, bindAddress, port);
	}

}

package edu.fra.uas.chat;

import java.io.IOException;

import edu.fra.uas.net.AbstractServer;

public class ChatServer extends AbstractServer{

	protected ChatServer(String bindAddress, int port) throws IOException {
		super(bindAddress, port);
	}

	@Override
	public void onReceive(byte[] receivedData, String srcAddress, int srcPort) {
		
	}

}

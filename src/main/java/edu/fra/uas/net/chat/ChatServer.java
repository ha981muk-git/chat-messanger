package edu.fra.uas.net.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.fra.uas.net.AbstractServer;
import edu.fra.uas.net.model.User;
import edu.fra.uas.net.utill.Log;
import edu.fra.uas.net.utill.Parser;

/**
 * This class presents a Socket server
 *
 * @author kalnaasan
 */
public class ChatServer extends AbstractServer {

    private final Log log = new Log();
    private List<User> users = new ArrayList<>();

    /**
     * default constructor
     *
     * @param bindAddress ip address of server
     * @param port        port of server
     */
    public ChatServer(String bindAddress, int port) throws IOException {
        super(bindAddress, port);
    }

    /**
     * Receives a request. When this method returns, the {@code receivedData}
     * parameter is filled with the data received. The received request also
     * contains the sender's IP address {@code srcAddress}, and the port number
     * {@code srcPort} on the sender's machine.
     * <p>
     * This method is called when ever a request is received. If the received
     * request message is longer than 1500 bytes the message is truncated.
     * <p>
     * If there is a security manager, a packet cannot be received if the security
     * manager's {@code checkAccept} method does not allow it.
     *
     * @param receivedData the {@code byte[]} into which to place the incoming data.
     * @param srcAddress   contains the sender's IP address
     * @param srcPort      contains the sender's port number
     */
    @Override
    public void onReceive(byte[] receivedData, String srcAddress, int srcPort) {
        try {
            int opcode = Parser.detectType(receivedData);
            switch (opcode) {
                case Parser.REGISTER:
                    User newClient = Parser.convertBytesToUser(receivedData);
                    users.add(newClient);
                    log.info("From: " + srcAddress + ":" + srcPort + " // " + newClient.getUsername());
                    break;
                case Parser.DEREGISTER:
                    User deletedUser = Parser.convertBytesToUser(receivedData);
                    users.remove(deletedUser);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
